package com.fashion_store.service;

import com.fashion_store.dto.request.AttributeRequest;
import com.fashion_store.dto.request.ListAttributeValueRequest;
import com.fashion_store.dto.response.AttributeResponse;
import com.fashion_store.entity.Attribute;
import com.fashion_store.entity.AttributeValue;
import com.fashion_store.enums.AttributeDisplayType;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.AttributeMapper;
import com.fashion_store.mapper.AttributeValueMapper;
import com.fashion_store.repository.AttributeRepository;
import com.fashion_store.repository.AttributeValueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeService extends GenerateService<Attribute, Long> {
    AttributeRepository attributeRepository;
    AttributeMapper attributeMapper;
    AttributeValueMapper attributeValueMapper;
    AttributeValueRepository attributeValueRepository;
    CloudinaryService cloudinaryService;

    @Override
    JpaRepository<Attribute, Long> getRepository() {
        return attributeRepository;
    }

    public AttributeResponse create(AttributeRequest request) {
        if (attributeRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);

        request.setDisplayType(request.getDisplayType().toUpperCase().trim());
        final Attribute attribute = attributeMapper.toAttribute(request);
        if (request.getDisplayType() == null) {
            attribute.setDisplayType(AttributeDisplayType.TEXT);
        } else {
            attribute.setDisplayType(AttributeDisplayType.valueOf(request.getDisplayType()));
        }

        List<AttributeValue> attributeValues = new ArrayList<>();
        for (int i = 0; i < request.getListAttributeValue().size(); i++) {
            ListAttributeValueRequest attributeValue = request.getListAttributeValue().get(i);
            AttributeValue attributeValueItem = attributeValueMapper.toAttributeValue(attributeValue);
            attributeValueItem.setAttribute(attribute);

            // handle image
            if (request.getImage() != null && !request.getImage().isEmpty() && i < request.getImage().size()) {
                MultipartFile img = request.getImage().get(i);
                if (img != null && !img.isEmpty()) {
                    try {
                        String imageUrl = cloudinaryService.uploadFile(request.getImage().get(i));
                        // Lưu URL vào DB
                        attributeValueItem.setImage(imageUrl);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
                    }
                }
            }
            attributeValues.add(attributeValueItem);
        }

        attribute.setAttributeValues(attributeValues);
        attributeRepository.save(attribute);

        AttributeResponse response = attributeMapper.toAttributeResponse(attribute);
        response.setAttributeDisplayType(attribute.getDisplayType());

        if (attribute.getAttributeValues() != null) {
            response.setListAttributeValue(attribute.getAttributeValues().stream()
                    .map(attributeValueMapper::toAttributeValueResponse).collect(Collectors.toList()));
        }

        return response;
    }

    public List<AttributeResponse> getAll() {
        return attributeRepository.findAll()
                .stream()
                .map(attribute -> {
                    AttributeResponse response = attributeMapper.toAttributeResponse(attribute);
                    response.setAttributeDisplayType(attribute.getDisplayType());
                    response.setListAttributeValue(attribute.getAttributeValues().stream()
                            .map(attributeValueMapper::toAttributeValueResponse).toList());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public AttributeResponse getInfo(Long id) {
        Attribute attribute = attributeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        AttributeResponse response = attributeMapper.toAttributeResponse(attribute);
        response.setAttributeDisplayType(attribute.getDisplayType());
        response.setListAttributeValue(attribute.getAttributeValues().stream()
                .map(attributeValueMapper::toAttributeValueResponse).toList());
        return response;
    }

    public AttributeResponse update(AttributeRequest request, Long id) {
        if (attributeRepository.existsByNameAndIdNot(request.getName(), id))
            throw new AppException(ErrorCode.EXISTED);
        Attribute attribute = attributeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        Map<Long, AttributeValue> attributeValueOld;
        if (attribute.getAttributeValues() != null && !attribute.getAttributeValues().isEmpty()) {
            attributeValueOld = attribute.getAttributeValues().stream()
                    .collect(Collectors.toMap(AttributeValue::getId, Function.identity()));
        } else {
            attributeValueOld = new HashMap<>();
        }

        if (request.getDisplayType() != null) {
            // set lại type do mapper bên dưới có thể lỗi nếu type không là UpperCase
            request.setDisplayType(request.getDisplayType().toUpperCase().trim());
            attribute.setDisplayType(AttributeDisplayType.valueOf(request.getDisplayType()));
        }
        attributeMapper.updateAttribute(attribute, request);


        // cập nhật list attribute value
        List<AttributeValue> newAttributeValues = new ArrayList<>();
        for (int i = 0; i < request.getListAttributeValue().size(); i++) {
            ListAttributeValueRequest item = request.getListAttributeValue().get(i);
            AttributeValue attributeValue = attributeValueMapper.toAttributeValue(item);
            if (item.getId() == null) {
                attributeValue.setAttribute(attribute);
                // handle image
                if (request.getImage() != null && !request.getImage().isEmpty() && i < request.getImage().size()) {
                    MultipartFile img = request.getImage().get(i);
                    if (img != null && !img.isEmpty()) {
                        try {
                            String imageUrl = cloudinaryService.uploadFile(request.getImage().get(i));
                            // Lưu URL vào DB
                            attributeValue.setImage(imageUrl);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
                        }
                    }
                }
                newAttributeValues.add(attributeValue);

            } else if (attributeValueOld.containsKey(item.getId())) {
                AttributeValue updateItem = attributeValueOld.get(item.getId());
                attributeValueMapper.updateAttributeValue(updateItem, item);
                // handle image
                if (request.getImage() != null && !request.getImage().isEmpty() && i < request.getImage().size()) {
                    MultipartFile img = request.getImage().get(i);
                    if (img != null && !img.isEmpty()) {
                        try {
                            String imageUrl = cloudinaryService.uploadFile(request.getImage().get(i));
                            // Lưu URL vào DB
                            updateItem.setImage(imageUrl);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
                        }
                    }
                }
                // trường hợp listAttributeValue có id trùng với id trước đó, xóa bỏ item cập nhật trước đó khỏi danh sách.
                //                "listAttributeValue":
                //                {"id": 38, "value": "Lam","color": "#fff" },
                //                {"id": 38, "value": "hồng","color": "#0ff" }
                newAttributeValues.removeIf(val -> val.getId() != null && val.getId().equals(updateItem.getId()));
                newAttributeValues.add(updateItem);
            }
        }

        attribute.getAttributeValues().clear();
        attribute.getAttributeValues().addAll(newAttributeValues);
        attributeRepository.save(attribute);

        // handle respone
        AttributeResponse response = attributeMapper.toAttributeResponse(attribute);

        response.setAttributeDisplayType(attribute.getDisplayType());

        if (attribute.getAttributeValues() != null) {
            response.setListAttributeValue(attribute.getAttributeValues().stream()
                    .map(attributeValueMapper::toAttributeValueResponse).collect(Collectors.toList()));
        }
        return response;
    }
}
