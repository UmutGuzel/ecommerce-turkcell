package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.repository.CategoryRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO:• Admin kullanıcılar yeni kategori ekleyebilmeli.

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
//    private final CategoryService categoryService;



    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
//        this.categoryService = categoryService;
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

//    @Override
//    public  List<CategoryListiningDto> getAllCategories() {
//        return categoryService.getAll();    }
//
//
//    @Override
//    public List<CategoryListiningDto> getCategoryById() {
//        return categoryService.getAll();
//    }

    @Override
    public CategoryDto getSubcategoriesByParentId(UUID parentId) {
        Category category = categoryRepository.findById(parentId).orElseThrow(() -> new BusinessException("Kategori bulunamadı."));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setSubCategories(
                category.getLinkedCategory().stream()
                        .map(subCategory -> new CategoryDto(subCategory.getId(), subCategory.getName()))
                        .toList()
        );
        return categoryDto;
    }


    @Override
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }




    public Boolean isCategoryNameExists(String name) {
        return categoryRepository.findByName(name).isPresent(); // Kategori adı varsa true döner
    }



    public CategoryDto createCategory(@Valid CreateCategoryDto createCategoryDto) {


        if (isCategoryNameExists(createCategoryDto.getName())) {
            throw new BusinessException("Bu kategori adı zaten mevcut.");
        }

        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setCreatedAt(new Date(System.currentTimeMillis()));
        category.setUpdatedAt(new Date(System.currentTimeMillis()));
        category = categoryRepository.save(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setSubCategories(
                category.getLinkedCategory().stream()
                        .map(subCategory -> new CategoryDto(subCategory.getId(), subCategory.getName()))
                        .toList()
        );

        return categoryDto;
    }


    public CategoryDto addSubcategory(UUID id, @Valid CreateCategoryDto createCategoryDto) {

        Category parentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Üst kategori bulunamadı."));


        Category subcategory = new Category();
        subcategory.setName(createCategoryDto.getName());
        subcategory.setParentCategory(parentCategory); // Üst kategoriye bağladım

        categoryRepository.save(subcategory); // Alt kategoriyi kaydettim

        parentCategory.getLinkedCategory().add(subcategory);
        Category category = categoryRepository.save(parentCategory); // Üst kategoriyi güncelledim

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setSubCategories(
                category.getLinkedCategory().stream()
                        .map(subCategory -> new CategoryDto(subCategory.getId(), subCategory.getName()))
                        .toList()
        );
        return categoryDto;
    }


    public boolean isCategoryAssociatedWithProducts(UUID categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }
    public void deleteCategory(UUID categoryId) {

        categoryRepository.deleteById(categoryId);
    }


}
