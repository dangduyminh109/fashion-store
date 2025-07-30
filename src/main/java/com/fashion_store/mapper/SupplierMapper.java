package com.fashion_store.mapper;

import com.fashion_store.dto.request.SupplierRequest;
import com.fashion_store.dto.response.SupplierResponse;
import com.fashion_store.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {
    Supplier toSupplier(SupplierRequest supplierRequest);

    SupplierResponse toSupplierResponse(Supplier supplier);

    void updateSupplier(@MappingTarget Supplier supplier, SupplierRequest supplierRequest);
}
