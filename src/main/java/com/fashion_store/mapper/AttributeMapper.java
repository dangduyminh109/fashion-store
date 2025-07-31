package com.fashion_store.mapper;

import com.fashion_store.dto.request.AttributeRequest;
import com.fashion_store.dto.response.AttributeResponse;
import com.fashion_store.entity.Attribute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttributeMapper {
    Attribute toAttribute(AttributeRequest attributeRequest);

    AttributeResponse toAttributeResponse(Attribute attribute);

    void updateAttribute(@MappingTarget Attribute attribute, AttributeRequest attributeRequest);
}
