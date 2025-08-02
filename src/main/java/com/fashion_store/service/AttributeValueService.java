package com.fashion_store.service;

import com.fashion_store.dto.request.AttributeValueRequest;
import com.fashion_store.dto.request.AttributeValueUpdateRequest;
import com.fashion_store.dto.response.AttributeValueResponse;
import com.fashion_store.entity.Attribute;
import com.fashion_store.entity.AttributeValue;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.AttributeValueMapper;
import com.fashion_store.repository.AttributeRepository;
import com.fashion_store.repository.AttributeValueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeValueService extends GenerateService<AttributeValue, Long> {
    AttributeValueRepository attributeValueRepository;
    CloudinaryService cloudinaryService;
    AttributeValueMapper attributeValueMapper;
    AttributeRepository attributeRepository;

    @Override
    JpaRepository<AttributeValue, Long> getRepository() {
        return attributeValueRepository;
    }

    public AttributeValueResponse create(AttributeValueRequest request) {
        Attribute parent = attributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));

        // kiểm tra thuộc tính có chứa value chưa
        parent.getAttributeValues().forEach(attributeValue -> {
            if (attributeValue.getValue().equals(request.getValue()))
                throw new AppException(ErrorCode.EXISTED);
        });

        AttributeValue attributeValue = attributeValueMapper.toAttributeValue(request);
        attributeValue.setAttribute(parent);

        // handle image
        if (!request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(request.getImage());
                attributeValue.setImage(imageUrl);

            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_SAVE_FAILED);
            }
        } else {
            attributeValue.setImage("");
        }
        attributeValue = attributeValueRepository.save(attributeValue);
        return attributeValueMapper.toAttributeValueResponse(attributeValue);
    }

    public List<AttributeValueResponse> getAll() {
        return attributeValueRepository.findAll()
                .stream()
                .map(attributeValueMapper::toAttributeValueResponse)
                .collect(Collectors.toList());
    }

    public AttributeValueResponse getInfo(Long id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return attributeValueMapper.toAttributeValueResponse(attributeValue);
    }

    public AttributeValueResponse update(AttributeValueUpdateRequest request, Long id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        // nếu value thay đổi thì kiểm tra xem có trùng với value nào khác trong thuộc tính k
        if (!request.getValue().equals(attributeValue.getValue())) {
            Attribute parent = attributeRepository.findById(attributeValue.getAttribute().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
            parent.getAttributeValues().forEach(attribute -> {
                if (attribute.getValue().equals(request.getValue()))
                    throw new AppException(ErrorCode.EXISTED);
            });
        }

        attributeValueMapper.updateAttributeValue(attributeValue, request);
        // handle image
        boolean imageDelete = request.getImageDelete() != null && request.getImageDelete();
        if (!request.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(request.getImage());
                // Lưu URL vào DB
                attributeValue.setImage(imageUrl);

            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_SAVE_FAILED);
            }
        } else if (imageDelete) {
            attributeValue.setImage("");
        }

        attributeValue = attributeValueRepository.save(attributeValue);
        return attributeValueMapper.toAttributeValueResponse(attributeValue);
    }
}
