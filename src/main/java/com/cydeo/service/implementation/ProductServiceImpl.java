package com.cydeo.service.implementation;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import com.cydeo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final MapperUtil mapperUtil;

    private final SecurityService securityService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
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

    @Override
    public List<ProductDto> getAllProducts() {

        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return productRepository
                .findAllByCategoryCompany(company)
                .stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(each -> mapperUtil.convert(each, new ProductDto()))
                .collect(Collectors.toList());
//below code is returning all company products, we need retrieve Only products of the current user's company should be listed in the list
//       List<Product> products = productRepository.findAll();
//      return products.stream().map( product -> mapperUtil.convert(product,new ProductDto())).collect(Collectors.toList());

    }

    @Override
    public void createProduct(ProductDto productDto) {
        productDto.setQuantityInStock(0);
        productRepository.save(mapperUtil.convert(productDto, new Product()));
    }

    @Override
    public void updateProduct(ProductDto productDto) {

        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(()-> new NoSuchElementException("Product " + productDto.getName() + "not found"));
        final int quantityInStock = productDto.getQuantityInStock() == null ? product.getQuantityInStock() : productDto.getQuantityInStock();
        productDto.setQuantityInStock(quantityInStock);
        productRepository.save(mapperUtil.convert(productDto,new Product()));
    }

    @Override
    public void delete(Long productId) {
      Product productEntity =  productRepository.findById(productId).get();
      if(productEntity.getQuantityInStock()==0){
          productEntity.setIsDeleted(true);
      }else {
          log.error("you cannot delete product that has quantity in stock");
          throw new RuntimeException();
      }
      productRepository.save(productEntity);
    }


}
