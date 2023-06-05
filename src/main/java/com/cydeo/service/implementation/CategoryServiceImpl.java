package com.cydeo.service.implementation;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private  final CategoryRepository categoryRepository;

private  final MapperUtil mapperUtil;

private final SecurityService securityService;

private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category category=  categoryRepository.findById(id).get();
        return  mapperUtil.convert(category,new CategoryDto());
    }

    @Override
    public List< CategoryDto> getListOfCategories() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return categoryRepository
                .findAllByCompany(company)
                .stream()
                .sorted(Comparator.comparing(Category::getDescription))
                .map(each -> {
                    CategoryDto dto = mapperUtil.convert(each, new CategoryDto());
                    dto.setHasProduct(hasProduct(dto.getId()));
                    return dto;
                }).collect(Collectors.toList());

//      List<  Category> categoryList = categoryRepository.findAll();
//        return  categoryList.stream().map( obj -> mapperUtil.convert(obj , new CategoryDto())).collect(Collectors.toList());

    }

    @Override
    public void create(CategoryDto categoryDto) {
        //avoid Company id null.
        categoryDto.setCompany(securityService.getLoggedInUser().getCompany());
        // get the new Dto category from view and convert to Entity then save it to database
        categoryRepository.save(mapperUtil.convert(categoryDto, new Category()));
    }

    @Override
    public void update(CategoryDto categoryDto) {
        /**
         *    Optional<ClientVendor> retrievedClientVendor = clientVendorRepository.findById(clientVendorDto.getId());
         *     clientVendorDto.getAddress().setId(retrievedClientVendor.get().getAddress().getId());     // otherwise it creates new address instead of updating existing one
         *      clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
         *        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
         *         clientVendorRepository.save(convertedClientVendor);
         */


        categoryDto.setCompany(securityService.getLoggedInUser().getCompany());
//        categoryDto.setDescription(categoryDto.getDescription());
        Category category = mapperUtil.convert(categoryDto, new Category());
        categoryRepository.save(category);
    }

    @Override
    public void delete(CategoryDto categoryDto) {
        Optional<Category> category= categoryRepository.findById(categoryDto.getId());
        category.get().setIsDeleted(true);
        category.get().setDescription(category.get().getDescription() + " " + category.get().getId());
        categoryRepository.save(category.get());
    }

    @Override
    public boolean hasProduct(Long categoryId) {
        return productService.findAllProductsWithCategoryId(categoryId).size() > 0;
    }

    @Override
    public boolean isCategoryDescriptionExist(CategoryDto categoryDto) {
        Company actualCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        Category existingCategory = categoryRepository.findByDescriptionAndCompany(categoryDto.getDescription(), actualCompany);
        if (existingCategory == null) return false;
        return !existingCategory.getId().equals(categoryDto.getId());
    }
}
