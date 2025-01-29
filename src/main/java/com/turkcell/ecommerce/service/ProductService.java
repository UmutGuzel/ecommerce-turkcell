package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    void add(CreateProductDto createProductDto);
    void update(UpdateProductDto updateProductDto);
    void delete(DeleteProductDto deleteProductDto);
    List<ProductListingDto> getAll(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock, String sortBy, String sortOrder);
    Optional<Product> findById(UUID id);
    Optional<Product> findProductsByCategoryId(UUID categoryId);
    boolean isProductExist(UUID categoryId);

    void decreaseStock(UUID id, Integer quantity);
    List<String> validateStock(Map<UUID, Integer> productQuantities);
}
