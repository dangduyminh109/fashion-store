package com.fashion_store.mapper;

import com.fashion_store.dto.request.AddressRequest;
import com.fashion_store.dto.request.AddressUpdateRequest;
import com.fashion_store.dto.response.AddressResponse;
import com.fashion_store.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
    Address toAddress(AddressRequest addressRequest);

    @Mapping(target = "customerId", source = "customer.id")
    AddressResponse toAddressResponse(Address address);

    void updateAddress(@MappingTarget Address address, AddressUpdateRequest addressUpdateRequest);
}
