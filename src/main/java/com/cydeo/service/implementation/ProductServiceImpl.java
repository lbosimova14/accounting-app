package com.cydeo.service.implementation;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProductDto findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return mapperUtil.convert(product, new ProductDto());

    }

    @Override
    public List<ProductDto> findAllProductsWithCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(productEntity -> mapperUtil.convert(productEntity, new ProductDto()))
                .collect(Collectors.toList());
    }
}
