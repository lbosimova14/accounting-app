package com.cydeo.repository;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //method returns to Entity, not Dto
    List<Product> findByCategoryId(Long categoryId);



    //join table
    List<Product> findAllByCategoryCompany(Company company);
}
