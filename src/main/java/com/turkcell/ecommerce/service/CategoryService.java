package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Optional<Category> findById(UUID id);
    Category createCategory(UUID id,CreateCategoryDto createCategoryDto);
    Category addSubcategory(UUID id,CreateCategoryDto createCategoryDto);
    List<CategoryListiningDto> getAll();

}
