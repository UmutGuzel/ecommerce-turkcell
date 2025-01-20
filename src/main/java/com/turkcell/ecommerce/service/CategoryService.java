package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Integer id);

}
