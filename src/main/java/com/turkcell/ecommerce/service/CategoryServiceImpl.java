package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.repository.CategoryRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO:• Admin kullanıcılar yeni kategori ekleyebilmeli.

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;



    public CategoryServiceImpl(@Lazy  CategoryRepository categoryRepository,@Lazy ProductRepository productRepository, @Lazy CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
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

    @Override
    public  List<CategoryListiningDto> getAllCategories() {
        return categoryService.getAll();    }


    @Override
    public List<CategoryListiningDto> getCategoryById() {
        return categoryService.getAll();
    }

    @Override
    public Optional<Category> getSubcategoriesByParentId(UUID parentId) {
        return categoryRepository.findById(parentId);
    }


    @Override
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }




    public Boolean isCategoryNameExists(String name) {
        return categoryRepository.findByName(name).isPresent(); // Kategori adı varsa true döner
    }



    public Category createCategory(@Valid CreateCategoryDto createCategoryDto) {


        if (isCategoryNameExists(createCategoryDto.getName())) {
            throw new IllegalArgumentException("Bu kategori adı zaten mevcut.");
        }

        Category category = new Category();
        category.setName(createCategoryDto.getName());
        return categoryRepository.save(category);
    }


    public Category addSubcategory(UUID ıd, @Valid CreateCategoryDto createCategoryDto) {

        Category parentCategory = categoryRepository.findById(ıd)
                .orElseThrow(() -> new IllegalArgumentException("Üst kategori bulunamadı."));


        Category subcategory = new Category();
        subcategory.setName(createCategoryDto.getName());
        subcategory.setParentCategory(parentCategory); // Üst kategoriye bağladım


        parentCategory.getLinkedCategory().add(subcategory);
        return categoryRepository.save(parentCategory); // Üst kategoriyi güncelledim
    }


}
