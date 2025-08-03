package com.fashion_store.mapper;

import com.fashion_store.dto.request.CustomerCreateRequest;
import com.fashion_store.dto.request.CustomerUpdateRequest;
import com.fashion_store.dto.response.CustomerResponse;
import com.fashion_store.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {AddressMapper.class})
public interface CustomerMapper {
    @Mapping(target = "avatar", ignore = true)
    Customer toCustomer(CustomerCreateRequest customerCreateRequest);

    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    void updateCustomer(@MappingTarget Customer customer, CustomerUpdateRequest customerUpdateRequest);
}
