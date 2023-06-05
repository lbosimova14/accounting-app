package com.cydeo.controller;

import com.cydeo.dto.ProductDto;
import com.cydeo.enums.ProductUnit;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listAllProducts(Model model) {

        model.addAttribute("products", productService.getAllProducts());
        return "/product/product-list";
    }

    @GetMapping("/create")
    public String navigateToProductCreatePage(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.getListOfCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        return "/product/product-create";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("newProduct") ProductDto productDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getListOfCategories());
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
            return "/product/product-create";
        }

        productService.createProduct(productDto);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, Model model) {

        model.addAttribute("product", productService.findProductById(productId));
        model.addAttribute("categories", categoryService.getListOfCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));


        return "/product/product-update";
    }

    //no need validation for update page, field is repopulated
    @PostMapping("/update/{id}")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getListOfCategories());
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
            return "/product/product-update";
        }
        productService.updateProduct(productDto);

        return "redirect:/products/list";
    }


    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId, Model model) {

        productService.delete(productId);
        return "redirect:/products/list";
    }


    @ModelAttribute
    public void commonAttributes(Model model) throws Exception {
        model.addAttribute("title", "Cydeo Accounting-Product");
    }
}
