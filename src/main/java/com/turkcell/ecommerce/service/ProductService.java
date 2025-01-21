package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    void add(CreateProductDto createProductDto);
    void update(UpdateProductDto updateProductDto);
    List<ProductListingDto> getAll();
    Optional<Product> findById(UUID id);
    Optional<Product> findProductsByCategoryId(UUID categoryId);



}
