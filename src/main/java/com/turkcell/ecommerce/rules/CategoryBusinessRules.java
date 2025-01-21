package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.repository.CategoryRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categoryMustExist(UUID id)
    {
        categoryRepository.findById(id).orElseThrow(() -> new BusinessException("Category not found"));
    }
}