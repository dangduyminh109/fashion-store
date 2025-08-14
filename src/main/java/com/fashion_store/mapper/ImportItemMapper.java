package com.fashion_store.mapper;

import com.fashion_store.dto.request.ImportItemRequest;
import com.fashion_store.dto.response.ImportItemResponse;
import com.fashion_store.entity.ImportItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {VariantMapper.class})
public interface ImportItemMapper {
    ImportItem toImportItem(ImportItemRequest importItemRequest);

    @Mapping(target = "sku", source = "variant.sku")
    @Mapping(target = "productName", source = "variant.product.name")
    // do mapstruct không tự động import thư viện nên phải thêm thủ công java.util...
    @Mapping(target = "displayName", expression = "java(importItem.getVariant().getAttributeValues() != null ? importItem.getVariant().getAttributeValues().stream().map(item -> item.getValue()).collect(java.util.stream.Collectors.joining(\"-\")) : \"\")")
    ImportItemResponse toImportItemResponse(ImportItem importItem);

    void updateImportItem(@MappingTarget ImportItem importItem, ImportItemRequest importItemRequest);
}
