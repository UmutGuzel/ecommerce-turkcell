package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findById(int id);
    Optional<Category> findByName(String name);
}
