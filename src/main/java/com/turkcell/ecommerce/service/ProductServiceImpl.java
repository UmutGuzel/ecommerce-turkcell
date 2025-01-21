package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public void add(CreateProductDto createProductDto) {


        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setStock(createProductDto.getStock());
        product.setImage(createProductDto.getImage());
        product.setCategory(category);


        productRepository.save(product);

    }

    public void update(UpdateProductDto updateProductDto) {
        Category category = categoryService
                .findById(updateProductDto.getId())
                .orElse(null);

        Product productToUpdate = productRepository.findById(updateProductDto.getId()).orElse(null);

        productToUpdate.setName(updateProductDto.getName());
        productToUpdate.setPrice(updateProductDto.getPrice());
        productToUpdate.setStock(updateProductDto.getStock());
        productToUpdate.setCategory(category);
        productToUpdate.setImage(updateProductDto.getImage());
        productRepository.save(productToUpdate);


    }

    public List<ProductListingDto> getAll() {

            List<ProductListingDto> productListingDtos = productRepository
                    .findAll()
                    .stream()
                    .map((product) -> new ProductListingDto(product.getId(), product.getName()))
                    .toList();

            return productListingDtos;

    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findProductsByCategoryId(UUID categoryId) {
       return productRepository.findById(categoryId);
    }


}
