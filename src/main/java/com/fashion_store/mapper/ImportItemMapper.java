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
    ImportItem toImportItem(ImportItemRequest supplierRequest);

    @Mapping(target = "sku", source = "variant.sku")
    @Mapping(target = "productName", source = "variant.product.name")
    ImportItemResponse toImportItemResponse(ImportItem supplier);

    void updateImportItem(@MappingTarget ImportItem supplier, ImportItemRequest supplierRequest);
}
