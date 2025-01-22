package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.repository.CategoryRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.repository.UserRepository;
import com.turkcell.ecommerce.util.exception.result.IllegalArgumentExceptionResult;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO:• Admin kullanıcılar yeni kategori ekleyebilmeli.

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }



    @Override
    public Category addSubcategory(UUID id,@Valid CreateCategoryDto createCategoryDto) {

        Category parentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent category ID."));

        Optional<Category> existingCategory = categoryRepository.findByName(createCategoryDto.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("This Subcategory name already exists.");
        }

        Category subCategory = new Category();
        subCategory.setName(createCategoryDto.getName());
        subCategory.setParent(parentCategory); //üst kategoriye bağlıyorum

        parentCategory.getSubCategories().add(subCategory);
        return categoryRepository.save(parentCategory); //ana kategoriyi güncelle
    }

    @Override
    public List<CategoryListiningDto> getAll() {
        List<CategoryListiningDto> categoryListiningDtos = categoryRepository
                .findAll()
                .stream()
                .map((category) -> new CategoryListiningDto(category.getId(), category.getName()))
                .toList();

        return categoryListiningDtos;
    }


    public void deleteCategory(UUID id) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Silinecek kategori bulunamadı."));

        Optional<Product> products = productRepository.findById(id);
        if (!products.isEmpty()) {
            throw new IllegalArgumentException("Bu kategori, ürünlerle ilişkilendirilmiş olduğundan silinemez.");
        }
        categoryRepository.delete(category);


    }



    @Override
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(UUID id, CreateCategoryDto createCategoryDto) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("admin"));
        if (!isAdmin) {
            throw new IllegalArgumentException("Bu işlemi gerçekleştirmek için admin rolüne sahip olmalısınız.");
        }

        Optional<Category> existingCategory=categoryRepository.findByName(createCategoryDto.getName());
        if(existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category name already exists");
        }

        Category category = new Category();
        category.setName(createCategoryDto.getName());
        return categoryRepository.save(category);
    }


}
