package com.fashion_store.mapper;

import com.fashion_store.dto.request.VariantCreateRequest;
import com.fashion_store.dto.request.VariantUpdateRequest;
import com.fashion_store.dto.response.VariantResponse;
import com.fashion_store.entity.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {AttributeValueMapper.class})
public interface VariantMapper {
    Variant toVariant(VariantCreateRequest variantCreateRequest);

    Variant toVariant(VariantUpdateRequest variantUpdateRequest);

    void updateVariant(@MappingTarget Variant variant, VariantUpdateRequest variantUpdateRequest);

    VariantResponse toVariantResponse(Variant variant);
}
