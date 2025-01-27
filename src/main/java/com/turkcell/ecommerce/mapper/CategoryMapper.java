package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;

public interface CategoryMapper {
    Category toEntity(CreateCategoryDto createCategoryDto);
    Category toEntityForSubcategory(CreateCategoryDto createCategoryDto, Category parentCategory);
    CategoryDto toDto(Category category);
    CategoryDto toDtoForSubcategory(Category category);
}
