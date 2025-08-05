package com.fashion_store.service;

import com.fashion_store.dto.request.CustomerCreateRequest;
import com.fashion_store.dto.request.CustomerUpdateRequest;
import com.fashion_store.dto.response.CustomerResponse;
import com.fashion_store.entity.Customer;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.CustomerMapper;
import com.fashion_store.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService extends GenerateService<Customer, String> {
    CustomerRepository customerRepository;
    CloudinaryService cloudinaryService;
    CustomerMapper customerMapper;
    PasswordEncoder passwordEncoder;

    @Override
    JpaRepository<Customer, String> getRepository() {
        return customerRepository;
    }

    public CustomerResponse create(CustomerCreateRequest request) {
        if (customerRepository.existsByFullName(request.getFullName()))
            throw new AppException(ErrorCode.EXISTED);
        if (customerRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EXISTED);
        if (customerRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.EXISTED);
        request.setAuthProvider(request.getAuthProvider().toUpperCase().trim());
        Customer customer = customerMapper.toCustomer(request);
        customer.setPassword(passwordEncoder.encode(request.getPassword()).trim());

        // handle avatar
        if (!request.getAvatar().isEmpty()) {
            try {
                String avatarUrl = cloudinaryService.uploadFile(request.getAvatar());
                customer.setAvatar(avatarUrl);

            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_SAVE_FAILED);
            }
        } else {
            customer.setAvatar("");
        }
        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    public List<CustomerResponse> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getInfo(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return customerMapper.toCustomerResponse(customer);
    }

    public void status(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        try {
            customer.setStatus(customer.getStatus() == null || !customer.getStatus());
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
        }
    }

    public CustomerResponse update(CustomerUpdateRequest request, String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (customerRepository.existsByFullNameAndIdNot(request.getFullName(), id))
            throw new AppException(ErrorCode.EXISTED);
        if (request.getEmail() != null && customerRepository.existsByEmailAndIdNot(request.getEmail(), id))
            throw new AppException(ErrorCode.EXISTED);
        if (request.getPhone() != null && customerRepository.existsByPhoneAndIdNot(request.getPhone(), id))
            throw new AppException(ErrorCode.EXISTED);

        if (request.getAuthProvider() != null) {
            request.setAuthProvider(request.getAuthProvider().toUpperCase().trim());
        }
        customerMapper.updateCustomer(customer, request);

        if (request.getOldPassword() != null && request.getNewPassword() != null) {
            boolean authenticated = passwordEncoder.matches(request.getOldPassword().trim(), customer.getPassword());
            if (authenticated) {
                customer.setPassword(passwordEncoder.encode(request.getNewPassword()).trim());
            } else {
                throw new AppException(ErrorCode.INVALID_PASSWORD);
            }
        }
        // handle avatar
        boolean avatarDelete = request.getAvatarDelete() != null && request.getAvatarDelete();
        if (!request.getAvatar().isEmpty()) {
            try {
                String avatarUrl = cloudinaryService.uploadFile(request.getAvatar());
                // Lưu URL vào DB
                customer.setAvatar(avatarUrl);
            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_SAVE_FAILED);
            }
        } else if (avatarDelete) {
            customer.setAvatar("");
        }

        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }
}
