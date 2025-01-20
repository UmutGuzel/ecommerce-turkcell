package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;

import java.util.List;

public interface ProductService {
    void add(CreateProductDto createProductDto);
    void update(UpdateProductDto updateProductDto);
    List<ProductListingDto> getAll();
}
