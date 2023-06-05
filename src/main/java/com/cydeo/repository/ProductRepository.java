package com.cydeo.repository;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //method returns to Entity, not Dto
    List<Product> findByCategoryId(Long categoryId);
}
