package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.entity.Product;

public interface ProductService {
    void add(CreateProductDto createProductDto);
}
