package com.fashion_store.service;

import com.fashion_store.dto.request.CategoryRequest;
import com.fashion_store.dto.response.CategoryResponse;
import com.fashion_store.entity.Category;
import com.fashion_store.mapper.CategoryMapper;
import com.fashion_store.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new RuntimeException("có lõi !!!");
        Category category = categoryMapper.toCategory(request);
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElse(null);
            category.setParent(parent);
        }
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse update(CategoryRequest request, Long id) {
        if (categoryRepository.existsByName(request.getName()))
            throw new RuntimeException("có lõi !!!");
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("có lõi !!!"));

        categoryMapper.updateCategory(category, request);
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElse(null);
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
