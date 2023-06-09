package com.cydeo.service;


import com.cydeo.dto.ProductDto;

import java.util.List;


public interface ProductService {
    ProductDto findProductById(Long productId);

    List<ProductDto> findAllProductsWithCategoryId(Long categoryId);

    List<ProductDto> getAllProducts();


    void createProduct(ProductDto productDto);

    void updateProduct(ProductDto productDto);

    void delete(Long productId);

    ProductDto update(Long id, ProductDto productDto);
}
