package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findCompanyById(Long id);

    List<CompanyDto> listAllCompany();


}
