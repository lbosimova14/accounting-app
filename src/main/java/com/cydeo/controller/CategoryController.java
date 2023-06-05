package com.cydeo.controller;

import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getListOfCategories(Model model) {
        model.addAttribute("categories", categoryService.getListOfCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String navigateToCategory(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("newCategory") CategoryDto categoryDto, BindingResult bindingResult) {

        boolean categoryDescriptionExist = categoryService.isCategoryDescriptionExist(categoryDto);

        if (categoryDescriptionExist) {
            bindingResult.rejectValue("description", " ", "This category description already exists");
        }
        if (bindingResult.hasErrors()) {
            return "/category/category-create";
        }

        categoryService.create(categoryDto);
        return "redirect:/categories/list";
    }


    @GetMapping("/update/{categoryId}")
    public String selectCategory(@PathVariable("categoryId") Long categoryId, Model model) {

        CategoryDto categoryById = categoryService.findCategoryById(categoryId);
        categoryById.setHasProduct(categoryService.hasProduct(categoryId));
        model.addAttribute("category", categoryById);
        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("category") CategoryDto categoryDto, BindingResult bindingResult, Model model) {

        boolean categoryDescriptionExist = categoryService.isCategoryDescriptionExist(categoryDto);
        if (categoryDescriptionExist) {
            bindingResult.rejectValue("description", " ", "This category description already exists");
        }

        if (bindingResult.hasErrors()) {
            categoryDto.setId(categoryDto.getId());
            return "/category/category-update";
        }

        categoryService.update(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@ModelAttribute("category") CategoryDto categoryDto) {

        categoryService.delete(categoryDto);
        return "redirect:/categories/list";

    }


    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("title", "Cydeo Accounting-Category");
    }


}
