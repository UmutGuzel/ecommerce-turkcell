package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    @PutMapping
    public void update(@RequestBody @Valid UpdateProductDto updateProductDto) {
        this.productService.update(updateProductDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable DeleteProductDto deleteProductDto) {
        this.productService.delete(deleteProductDto);
    }

    @GetMapping
    public List<ProductListingDto> getAll() {
        return this.productService.getAll();
    }
}
