package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.mapper.ProductMapper;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.rules.CategoryBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
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

    @Override
    public List<ProductListingDto> getAll(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock, String sortBy, String sortOrder) {
        if (categoryName == null) categoryName = "";
        if (minPrice == null) minPrice = BigDecimal.ZERO;
        if (maxPrice == null) maxPrice = BigDecimal.valueOf(Integer.MAX_VALUE);
        if (inStock == null) inStock = true;
        if (sortBy == null) sortBy = "price";
        if (sortOrder == null) sortOrder = "ASC";

        List<Product> products = productRepository.findProductsWithFilters(categoryName, minPrice, maxPrice, inStock);

        Comparator<Product> comparator;
        if ("price".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if ("name".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getName);
        } else if ("stock".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getStock);
        } else {
            comparator = Comparator.comparing(Product::getPrice);
        }

        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);

        return productMapper.toProductListingDto(products);
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
