package com.fashion_store.service;

import com.fashion_store.Utils.SecurityUtils;
import com.fashion_store.dto.request.CustomerCreateRequest;
import com.fashion_store.dto.request.OrderCreateRequest;
import com.fashion_store.dto.request.OrderUpdateRequest;
import com.fashion_store.dto.response.OrderResponse;
import com.fashion_store.entity.*;
import com.fashion_store.enums.AuthProvider;
import com.fashion_store.enums.DiscountType;
import com.fashion_store.enums.OrderStatus;
import com.fashion_store.enums.PaymentMethod;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.CustomerMapper;
import com.fashion_store.mapper.OrderItemMapper;
import com.fashion_store.mapper.OrderMapper;
import com.fashion_store.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService extends GenerateService<Order, String> {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderItemMapper orderItemMapper;
    CustomerRepository customerRepository;
    VoucherRepository voucherRepository;
    VariantRepository variantRepository;
    CustomerMapper customerMapper;

    @Override
    JpaRepository<Order, String> getRepository() {
        return orderRepository;
    }

    public List<OrderResponse> getAll(boolean deleted) {
        return orderRepository.findAll()
                .stream().filter(item -> item.getIsDeleted() == deleted)
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getInfo(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return orderMapper.toOrderResponse(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createClient(OrderCreateRequest request) {
        String customerId = SecurityUtils.getCurrentUserId();
        Order order = createOrder(request, customerId);
        return orderMapper.toOrderResponse(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderResponse create(OrderCreateRequest request) {
        Order order = createOrder(request, request.getCustomerId());
        return orderMapper.toOrderResponse(order);
    }

    public Order createOrder(OrderCreateRequest request, String customerId) {
        Order order = orderMapper.toOrder(request);
        if (customerId != null && !customerId.isBlank() && !customerId.equals("anonymousUser")) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            order.setCustomer(customer);
        } else {
            CustomerCreateRequest newCustomer = CustomerCreateRequest.builder()
                    .fullName(request.getCustomerName())
                    .phone(request.getPhone())
                    .isGuest(true)
                    .authProvider("GUEST")
                    .build();
            Customer customer = customerMapper.toCustomer(newCustomer);
            customerRepository.save(customer);
            order.setCustomer(customer);
        }
        if (request.getVoucherId() != null) {
            Voucher voucher = voucherRepository.findById(request.getVoucherId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            if (voucher.getStartDate() != null && voucher.getStartDate().isAfter(LocalDateTime.now()))
                throw new AppException(ErrorCode.VOUCHER_NOT_STARTED);
            if (voucher.getEndDate() != null && voucher.getEndDate().isBefore(LocalDateTime.now()))
                throw new AppException(ErrorCode.VOUCHER_EXPIRED);
            if ((long) voucher.getOrders().size() >= voucher.getQuantity())
                throw new AppException(ErrorCode.VOUCHER_USAGE_LIMIT_EXCEEDED);

            order.setVoucher(voucher);
            order.setVoucherName(voucher.getName());
            order.setDiscountType(voucher.getDiscountType());
        }

        List<OrderItem> orderItems = new ArrayList<>();
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};
        Order finalOrder = order;
        request.getOrderItems()
                .forEach(item -> {
                    OrderItem orderItem = orderItemMapper.toOrderItem(item);
                    Variant variant = variantRepository.findById(item.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                    if (variant.getStatus() == false || variant.getIsDeleted() == true)
                        throw new AppException(ErrorCode.VARIANT_NOT_AVAILABLE);
                    if (variant.getInventory() < item.getQuantity())
                        throw new AppException(ErrorCode.INVALID_QUANTITY_UPDATE);
                    orderItem.setVariant(variant);
                    orderItem.setProductName(variant.getProduct().getName());

                    if (variant.getPromotionalPrice() != null
                            && (variant.getPromotionStartTime() != null && variant.getPromotionStartTime().isBefore(LocalDateTime.now()))
                            && (variant.getPromotionEndTime() != null && variant.getPromotionEndTime().isAfter(LocalDateTime.now()))) {
                        orderItem.setPrice(variant.getPromotionalPrice());
                    } else {
                        orderItem.setPrice(variant.getSalePrice());
                    }

                    orderItem.setOrder(finalOrder);
                    totalAmount[0] = totalAmount[0].add(
                            orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    );
                    orderItems.add(orderItem);
                });

        order.setOrderItems(orderItems);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount[0]);
        order.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase().trim()));
        if (order.getVoucher() != null) {
            if (order.getVoucher().getDiscountType().equals(DiscountType.PERCENT)) {
                BigDecimal discountValue = order.getTotalAmount()
                        .multiply(order.getVoucher().getDiscountValue())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                if (discountValue.compareTo(order.getVoucher().getMaxDiscountValue()) > 0) {
                    discountValue = order.getVoucher().getMaxDiscountValue();
                }
                order.setTotalDiscount(discountValue);
            } else {
                order.setTotalDiscount(order.getVoucher().getDiscountValue());
            }
        }
        order.setFinalAmount(
                order.getTotalAmount().subtract(order.getTotalDiscount()).max(BigDecimal.ZERO)
        );

        order = orderRepository.save(order);
        return order;
    }

    public OrderResponse update(OrderUpdateRequest request, String id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));

        // cập nhật status và paymethod
        if (request.getOrderStatus() != null) {
            try {
                OrderStatus status = OrderStatus.valueOf(request.getOrderStatus().toUpperCase().trim());
                order.setOrderStatus(status);
            } catch (Exception e) {
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
            }
        }
        if (request.getPaymentMethod() != null) {
            try {
                PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase().trim());
                order.setPaymentMethod(paymentMethod);
            } catch (Exception e) {
                throw new AppException(ErrorCode.INVALID_PAYMENT_METHOD);
            }

        }

        orderMapper.updateOrder(order, request);
        if (request.getIsPaid() == true && order.getIsPaid() != null) {
            order.setIsPaid(true);
            order.setPaidAt(LocalDateTime.now());
        } else if (!request.getIsPaid()) {
            order.setIsPaid(false);
            order.setPaidAt(null);
        }

        // khi thay đổi cuttomer
        // th1: nếu trước đó là customer guest và không có order nào khác ngoài order hiện tại
        // thì xóa customer guest đó, gắm order hiện tại vào cho customer mới (không phải guest)
        // th2: nếu trước đó không là customer guest và khi cập nhật trở thành guest
        // thì tạo customer guest mới là chủ của order đó
        Customer deleteCustomer = null;
        if (!request.getCustomerId().equals(order.getCustomer().getId()) && request.getCustomerId() != null) {
            if (!request.getCustomerId().isBlank()) {
                long otherOrders = order.getCustomer().getOrders().stream()
                        .filter(o -> !o.getId().equals(order.getId()))
                        .count();
                Customer newCustomer = customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                Customer oldCustomer = order.getCustomer();
                order.setCustomer(newCustomer);
                if (oldCustomer.getAuthProvider().equals(AuthProvider.GUEST)
                        && otherOrders == 0
                ) {
                    deleteCustomer = oldCustomer;
                }
            } else if (request.getCustomerId().isEmpty()) {
                if (!order.getCustomer().getAuthProvider().equals(AuthProvider.GUEST)) {
                    CustomerCreateRequest newCustomer = CustomerCreateRequest.builder()
                            .fullName(request.getCustomerName())
                            .phone(request.getPhone())
                            .isGuest(true)
                            .authProvider("GUEST")
                            .build();
                    Customer customer = customerMapper.toCustomer(newCustomer);
                    customerRepository.save(customer);
                    order.setCustomer(customer);
                }
            }
        }

        // Cập nhật voucher
        if (request.getVoucherId() != null
                && (order.getVoucher() == null || !order.getVoucher().getId().equals(request.getVoucherId()))) {
            Voucher voucher = voucherRepository.findById(request.getVoucherId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            if (voucher.getStartDate() != null && voucher.getStartDate().isAfter(LocalDateTime.now()))
                throw new AppException(ErrorCode.VOUCHER_NOT_STARTED);
            if (voucher.getEndDate() != null && voucher.getEndDate().isBefore(LocalDateTime.now()))
                throw new AppException(ErrorCode.VOUCHER_EXPIRED);
            if ((long) voucher.getOrders().size() >= voucher.getQuantity())
                throw new AppException(ErrorCode.VOUCHER_USAGE_LIMIT_EXCEEDED);
            order.setVoucher(voucher);
            order.setVoucherName(voucher.getName());
            order.setDiscountType(voucher.getDiscountType());
        }

        // cập nhật order item list
        if (request.getOrderItems() != null) {
            order.getOrderItems().clear();
            List<OrderItem> newOrderItems = new ArrayList<>();
            final BigDecimal[] totalAmount = {BigDecimal.ZERO};
            request.getOrderItems()
                    .forEach(item -> {
                        OrderItem orderItem = orderItemMapper.toOrderItem(item);
                        Variant variant = variantRepository.findById(item.getVariantId())
                                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                        if (variant.getStatus() == false || variant.getIsDeleted() == true)
                            throw new AppException(ErrorCode.VARIANT_NOT_AVAILABLE);
                        if (variant.getInventory() < item.getQuantity())
                            throw new AppException(ErrorCode.INVALID_QUANTITY_UPDATE);
                        orderItem.setVariant(variant);
                        orderItem.setProductName(variant.getProduct().getName());

                        if (variant.getPromotionalPrice() != null
                                && (variant.getPromotionStartTime() != null && variant.getPromotionStartTime().isBefore(LocalDateTime.now()))
                                && (variant.getPromotionEndTime() != null && variant.getPromotionEndTime().isAfter(LocalDateTime.now()))) {
                            orderItem.setPrice(variant.getPromotionalPrice());
                        } else {
                            orderItem.setPrice(variant.getSalePrice());
                        }

                        orderItem.setOrder(order);
                        totalAmount[0] = totalAmount[0].add(
                                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                        );
                        newOrderItems.add(orderItem);
                    });
            order.getOrderItems().addAll(newOrderItems);

            // cập nhật total amount
            order.setTotalAmount(totalAmount[0]);
            if (order.getVoucher() != null) {
                if (order.getVoucher().getDiscountType().equals(DiscountType.PERCENT)) {
                    BigDecimal discountValue = order.getTotalAmount()
                            .multiply(order.getVoucher().getDiscountValue())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                    if (discountValue.compareTo(order.getVoucher().getMaxDiscountValue()) > 0) {
                        discountValue = order.getVoucher().getMaxDiscountValue();
                    }
                    order.setTotalDiscount(discountValue);
                } else {
                    order.setTotalDiscount(order.getVoucher().getDiscountValue());
                }
            }
            BigDecimal total = order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO;
            BigDecimal discount = order.getTotalDiscount() != null ? order.getTotalDiscount() : BigDecimal.ZERO;
            order.setFinalAmount(total.subtract(discount).max(BigDecimal.ZERO));
        }

        orderRepository.save(order);
        if (deleteCustomer != null)
            customerRepository.delete(deleteCustomer);
        return orderMapper.toOrderResponse(order);
    }

    public void status(String id, String status) {
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase().trim()));
            orderRepository.save(order);

        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }
}
