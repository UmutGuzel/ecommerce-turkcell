package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CategoryMapperImpl implements CategoryMapper{
    @Override
    public Category toEntity(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setCreatedAt(new Date(System.currentTimeMillis()));
        category.setUpdatedAt(new Date(System.currentTimeMillis()));
        return category;
    }

    @Override
    public Category toEntityForSubcategory(CreateCategoryDto createCategoryDto, Category parentCategory) {
        Category subcategory = new Category();
        subcategory.setName(createCategoryDto.getName());
        subcategory.setParentCategory(parentCategory); // Üst kategoriye bağladım
        return subcategory;
    }

    @Override
    public CategoryDto toDto(Category category) {
        return null;
    }

    @Override
    public CategoryDto toDtoForSubcategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setSubCategories(
                category.getLinkedCategory().stream()
                        .map(subCategory -> new CategoryDto(subCategory.getId(), subCategory.getName()))
                        .toList()
        );
        return categoryDto;
    }
}
