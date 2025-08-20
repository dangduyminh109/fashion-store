package com.fashion_store.mapper;

import com.fashion_store.dto.variant.request.VariantCreateRequest;
import com.fashion_store.dto.variant.request.VariantUpdateRequest;
import com.fashion_store.dto.variant.response.VariantClientResponse;
import com.fashion_store.entity.Variant;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {AttributeValueMapper.class})
public interface VariantMapper {
    Variant toVariant(VariantCreateRequest variantCreateRequest);

    Variant toVariant(VariantUpdateRequest variantUpdateRequest);

    void updateVariant(@MappingTarget Variant variant, VariantUpdateRequest variantUpdateRequest);

    @Mapping(target = "product.variants", ignore = true)
    @Mapping(target = "product.productImages", expression = "java(product.getProductImages() != null ? product.getProductImages().stream().map(img->img.getUrl()).toList() : null)")
    VariantClientResponse toVariantClientResponse(Variant variant);
}
