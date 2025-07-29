package com.fashion_store.mapper;

import com.fashion_store.dto.request.CategoryRequest;
import com.fashion_store.dto.response.CategoryResponse;
import com.fashion_store.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "image", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);

    @Mapping(target = "parentName", expression = "java(category.getParent() != null ? category.getParent().getName() : null)")
    @Mapping(target = "parentId", expression = "java(category.getParent() != null ? category.getParent().getId() : null)")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
