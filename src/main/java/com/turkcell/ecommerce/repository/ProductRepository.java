package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
    Optional<Product> findById(UUID id);
    boolean existsByNameAndIdNot(String name, UUID id);

}
