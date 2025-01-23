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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Yeni bir kategori oluşturma
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category createdCategory = categoryService.createCategory(createCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // Alt kategori ekleme
    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<Category> addSubcategory(
            @PathVariable UUID parentId,
            @Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category createdSubcategory = categoryService.addSubcategory(parentId, createCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }



    @GetMapping
    public  List<CategoryListiningDto> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public List<CategoryListiningDto> getCategoryById(@PathVariable UUID id) {
        return this.categoryService.getCategoryById();
    }

    @GetMapping("/{parentId}/subcategories")
    public Optional<Category> getSubcategories(@PathVariable UUID parentId) {
        return categoryService.getSubcategoriesByParentId(parentId);
    }


}
