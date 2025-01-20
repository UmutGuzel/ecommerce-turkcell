package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }


}
