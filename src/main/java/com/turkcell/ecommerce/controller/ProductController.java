package com.turkcell.ecommerce.controller;


import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public void add(@RequestBody @Valid CreateProductDto createProductDto) {
        this.productService.add(createProductDto);

    }
    @GetMapping
    public List<ProductListingDto> getAll() {
        return this.productService.getAll();
    }


}
