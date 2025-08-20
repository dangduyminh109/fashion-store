package com.fashion_store.mapper;

import com.fashion_store.dto.order.request.OrderItemRequest;
import com.fashion_store.dto.order.response.OrderItemResponse;
import com.fashion_store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {
    OrderItem toOrderItem(OrderItemRequest addressRequest);

    OrderItemResponse toOrderItemResponse(OrderItem address);

    void updateOrderItem(@MappingTarget OrderItem address, OrderItemRequest addressUpdateRequest);
}
