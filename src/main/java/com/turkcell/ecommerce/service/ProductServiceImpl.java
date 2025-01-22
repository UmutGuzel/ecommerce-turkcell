package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.mapper.ProductMapper;
import com.turkcell.ecommerce.mapper.ProductMapperImpl;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.rules.CategoryBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryBusinessRules categoryBusinessRules;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryBusinessRules categoryBusinessRules, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryBusinessRules = categoryBusinessRules;
        this.productMapper = productMapper;
    }

    // TODO [LOW]: Hata kontrolleri business rules a yazılacak.
    public void add(CreateProductDto createProductDto) {
        categoryBusinessRules.categoryMustExist(createProductDto.getCategoryId());

        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        Product productWithSameName = productRepository
                .findByName(createProductDto.getName())
                .orElse(null);

        if(productWithSameName != null)
            throw new BusinessException("Product already exists");

        Product product = productMapper.toEntity(createProductDto, category);
        productRepository.save(product);
    }

    public void update(UpdateProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(updateProductDto.getId())
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));

        boolean isDuplicateName = productRepository.existsByNameAndIdNot(updateProductDto.getName(), updateProductDto.getId());
        if (isDuplicateName) {
            throw new BusinessException("Aynı isimde başka bir ürün mevcut.");
        }

        productMapper.updateEntity(updateProductDto, existingProduct);
        productRepository.save(existingProduct);
    }

    @Override
    public void delete(DeleteProductDto deleteProductDto) {
        Product product = productRepository.findById(deleteProductDto.getId())
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));

        // TODO [HIGH]: Ürün, siparişlerle ilişikili mi kontrolü düzenlenecek.
        if (product.getOrderItems() != null && !product.getOrderItems().isEmpty()) {
            throw new BusinessException("Bu ürün, siparişlerle ilişkilendirildiği için silinemez.");
        }

        productRepository.delete(product);
    }

    public List<ProductListingDto> getAll() {

            List<Product> products = productRepository.findAll();

        return productMapper.toProductListingDto(products);
//            List<ProductListingDto> productListingDtos = productRepository
//                    .findAll()
//                    .stream()
//                    .map((product) -> new ProductListingDto(
//                            product.getId(),
//                            product.getName(),
//                            product.getPrice(),
//                            product.getStock(),
//                            product.getCategory().getName(),
//                            product.getDescription()))
//                    .toList();
//
//            return productListingDtos;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findProductsByCategoryId(UUID categoryId) {
        return Optional.empty();
    }
}
