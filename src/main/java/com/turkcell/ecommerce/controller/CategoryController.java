package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category createdCategory = categoryService.createCategory(createCategoryDto); // Kategori oluşturuyor
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory); // 201  ile birlikte döndür
    }

    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<Category> addSubcategory(@PathVariable Integer id, @Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category createdSubcategory = categoryService.addSubcategory(id, createCategoryDto); // Alt kategori ekle
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }

    @GetMapping
    public List<CategoryListiningDto> getAll() {
        return this.categoryService.getAll();
    }

}