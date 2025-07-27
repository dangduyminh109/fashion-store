package com.fashion_store.mapper;

import com.fashion_store.Utils.GenerateSlugUtils;
import com.fashion_store.dto.request.CategoryRequest;
import com.fashion_store.dto.response.CategoryResponse;
import com.fashion_store.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = GenerateSlugUtils.class)
public interface CategoryMapper {
    @Mapping(target = "slug", expression = "java(GenerateSlugUtils.generateSlug(categoryRequest.getName()))")
    @Mapping(target = "parent", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);

    @Mapping(target = "parentId", expression = "java(category.getParent() != null ? category.getParent().getId() : null)")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "slug", expression = "java(GenerateSlugUtils.generateSlug(categoryRequest.getName()))")
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
