package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.category.CategoryDto;
import com.turkcell.ecommerce.dto.category.CategoryListiningDto;
import com.turkcell.ecommerce.dto.category.CreateCategoryDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.mapper.CategoryMapper;
import com.turkcell.ecommerce.repository.CategoryRepository;
import com.turkcell.ecommerce.rules.CategoryBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryBusinessRules categoryBusinessRules;
    private final CategoryMapper categoryMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy ProductService productService, CategoryBusinessRules categoryBusinessRules, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.categoryBusinessRules = categoryBusinessRules;
        this.categoryMapper = categoryMapper;
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
    public CategoryDto getSubcategoriesByParentId(UUID parentId) {
        Category category = categoryRepository.findById(parentId).orElseThrow(() -> new BusinessException("Kategori bulunamadı."));
        return categoryMapper.toDtoForSubcategory(category);
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

        Category category = categoryMapper.toEntity(createCategoryDto);

        category = categoryRepository.save(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
//        categoryDto.setSubCategories(
//                category.getLinkedCategory().stream()
//                        .map(subCategory -> new CategoryDto(subCategory.getId(), subCategory.getName()))
//                        .toList()
//        );

        return categoryDto;
    }


    public CategoryDto addSubcategory(UUID id, @Valid CreateCategoryDto createCategoryDto) {

        Category parentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Üst kategori bulunamadı."));

        Category subcategory = categoryMapper.toEntityForSubcategory(createCategoryDto, parentCategory);

        categoryRepository.save(subcategory); // Alt kategoriyi kaydettim

        parentCategory.getLinkedCategory().add(subcategory);
        Category category = categoryRepository.save(parentCategory); // Üst kategoriyi güncelledim

        return categoryMapper.toDtoForSubcategory(category);
    }

    public void deleteCategory(UUID categoryId) {
        boolean isExist = productService.isProductExist(categoryId);
        categoryBusinessRules.productExistWithCategory(isExist);
        categoryRepository.deleteById(categoryId);
    }


}
