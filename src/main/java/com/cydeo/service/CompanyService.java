package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findCompanyById(Long id);

    List<CompanyDto> listAllCompany();


   CompanyDto save(CompanyDto companyDto);

    void update(CompanyDto companyDto);
    boolean isTitleExist(String title);

    void activate(Long byId);

    void deactivate(Long companyId);

    List<CompanyDto> getFilteredCompaniesForCurrentUser();

    CompanyDto getCompanyByLoggedInUser();
}
