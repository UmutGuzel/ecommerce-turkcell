package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//İD KISIMINI UUİD YAPTIK

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
    Optional<Product> findByNameIsAndIdIsNot(String name, UUID id);
}
