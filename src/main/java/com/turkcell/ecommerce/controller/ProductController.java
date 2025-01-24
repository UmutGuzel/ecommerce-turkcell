package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody @Valid CreateProductDto createProductDto) {
        this.productService.add(createProductDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Ürün başarıyla eklendi.");
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateProductDto updateProductDto) {
        this.productService.update(updateProductDto);
    }

    @DeleteMapping
    public void delete(@RequestBody @Valid DeleteProductDto deleteProductDto) {
        this.productService.delete(deleteProductDto);
    }

    @GetMapping()
    public List<ProductListingDto> getAll(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        return productService.getAll(categoryName, minPrice, maxPrice, inStock, sortBy, sortOrder);
    }
}
