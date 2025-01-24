package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.service.CategoryService;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
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

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(createCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<CategoryDto> addSubcategory(
            @PathVariable UUID parentId,
            @Valid @RequestBody CreateCategoryDto createCategoryDto) {
        CategoryDto createdSubcategory = categoryService.addSubcategory(parentId, createCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }



    @GetMapping
    public  List<CategoryListiningDto> getAllCategories() {
        return this.categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable UUID id) {
        return this.categoryService.getSubcategoriesByParentId(id);
    }

    @GetMapping("/{parentId}/subcategories")
    public CategoryDto getSubcategories(@PathVariable UUID parentId) {
        return categoryService.getSubcategoriesByParentId(parentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        if (categoryService.isCategoryAssociatedWithProducts(id)) {
            throw new BusinessException("Kategori, ürünlerle ilişkilendirildiği için silinemez.");
        }

        // Kategoriyi siliyorum
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
