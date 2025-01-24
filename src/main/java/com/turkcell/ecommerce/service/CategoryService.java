package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Optional<Category> findById(UUID id);
    CategoryDto createCategory(CreateCategoryDto createCategoryDto);
    CategoryDto addSubcategory(UUID id,CreateCategoryDto createCategoryDto);
    List<CategoryListiningDto> getAll();
    CategoryDto getSubcategoriesByParentId(UUID parentId);
    void deleteCategory(UUID id);


    boolean isCategoryAssociatedWithProducts(UUID id);
}
