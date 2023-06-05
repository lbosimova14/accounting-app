package com.cydeo.service;

import com.cydeo.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto findCategoryById(Long id);

    List<CategoryDto> getListOfCategories();

    void create(CategoryDto categoryDto);

    void update(CategoryDto categoryDto);

    void delete(CategoryDto categoryDto);

    boolean hasProduct(Long categoryId);

    boolean isCategoryDescriptionExist(CategoryDto categoryDto);
}
