package com.fashion_store.service;

import com.fashion_store.dto.request.SupplierRequest;
import com.fashion_store.dto.response.SupplierResponse;
import com.fashion_store.entity.Supplier;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.SupplierMapper;
import com.fashion_store.repository.SupplierRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierService extends GenerateService<Supplier, Long> {
    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    @Override
    JpaRepository<Supplier, Long> getRepository() {
        return supplierRepository;
    }

    public SupplierResponse create(SupplierRequest request) {
        if (supplierRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Supplier supplier = supplierMapper.toSupplier(request);

        supplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    public List<SupplierResponse> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toSupplierResponse)
                .collect(Collectors.toList());
    }

    public SupplierResponse getInfo(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return supplierMapper.toSupplierResponse(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (supplierRepository.existsByNameAndIdNot(request.getName(), id))
            throw new AppException(ErrorCode.EXISTED);
        supplierMapper.updateSupplier(supplier, request);

        supplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    public void status(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        try {
            supplier.setStatus(supplier.getStatus() == null || !supplier.getStatus());
            supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
        }
    }
}
