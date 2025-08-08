package com.fashion_store.mapper;

import com.fashion_store.dto.request.AttributeValueRequest;
import com.fashion_store.dto.request.AttributeValueUpdateRequest;
import com.fashion_store.dto.request.AttributeValueItemRequest;
import com.fashion_store.dto.response.AttributeValueResponse;
import com.fashion_store.entity.AttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttributeValueMapper {
    @Mapping(target = "image", ignore = true)
    AttributeValue toAttributeValue(AttributeValueRequest attributeValueRequest);

    @Mapping(target = "image", ignore = true)
    AttributeValue toAttributeValue(AttributeValueItemRequest attributeValueUpdateRequest);

    @Mapping(target = "attributeId", expression = "java(attributeValue.getAttribute() != null ? attributeValue.getAttribute().getId() : null)")
    @Mapping(target = "attributeName", source = "attribute.name")
    AttributeValueResponse toAttributeValueResponse(AttributeValue attributeValue);

    @Mapping(target = "image", ignore = true)
    void updateAttributeValue(@MappingTarget AttributeValue attributeValue, AttributeValueUpdateRequest attributeValueUpdateRequest);

    @Mapping(target = "image", ignore = true)
    void updateAttributeValue(@MappingTarget AttributeValue attributeValue, AttributeValueItemRequest attributeValueItemRequest);
}
