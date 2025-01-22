package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
    Optional<Product> findById(UUID id);
    boolean existsByNameAndIdNot(String name, UUID id);

    @Query("SELECT p FROM Product p WHERE p.category.name LIKE %:categoryName% AND p.price BETWEEN :minPrice AND :maxPrice AND (:inStock IS NULL OR (p.stock > 0 AND :inStock = true) OR (p.stock = 0 AND :inStock = false))")
    List<Product> findProductsWithFilters(
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock);
}
