package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Optional<Category> findById(int id);

}
