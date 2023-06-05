package com.cydeo.service.implementation;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CompanyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {
    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @MockBean
    private MapperUtil mapperUtil;

    /**
     * Method under test: {@link CompanyServiceImpl#update(CompanyDto)}
     */
    @Test
    void testUpdate() {
        Address address = new Address();
        address.setAddressLine1("42 Main St");
        address.setAddressLine2("42 Main St");
        address.setCity("Oxford");
        address.setCountry("GB");
        address.setId(1L);
        address.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address.setInsertUserId(1L);
        address.setIsDeleted(true);
        address.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address.setLastUpdateUserId(1L);
        address.setState("MD");
        address.setZipCode("21654");

        Company company = new Company();
        company.setAddress(address);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setId(1L);
        company.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company.setInsertUserId(1L);
        company.setIsDeleted(true);
        company.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company.setLastUpdateUserId(1L);
        company.setPhone("6625550144");
        company.setTitle("Dr");
        company.setWebsite("Website");
        Optional<Company> ofResult = Optional.of(company);

        Address address2 = new Address();
        address2.setAddressLine1("42 Main St");
        address2.setAddressLine2("42 Main St");
        address2.setCity("Oxford");
        address2.setCountry("GB");
        address2.setId(1L);
        address2.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address2.setInsertUserId(1L);
        address2.setIsDeleted(true);
        address2.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address2.setLastUpdateUserId(1L);
        address2.setState("MD");
        address2.setZipCode("21654");

        Company company2 = new Company();
        company2.setAddress(address2);
        company2.setCompanyStatus(CompanyStatus.ACTIVE);
        company2.setId(1L);
        company2.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company2.setInsertUserId(1L);
        company2.setIsDeleted(true);
        company2.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company2.setLastUpdateUserId(1L);
        company2.setPhone("6625550144");
        company2.setTitle("Dr");
        company2.setWebsite("Website");
        when(companyRepository.save(Mockito.<Company>any())).thenReturn(company2);
        when(companyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Address address3 = new Address();
        address3.setAddressLine1("42 Main St");
        address3.setAddressLine2("42 Main St");
        address3.setCity("Oxford");
        address3.setCountry("GB");
        address3.setId(1L);
        address3.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address3.setInsertUserId(1L);
        address3.setIsDeleted(true);
        address3.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        address3.setLastUpdateUserId(1L);
        address3.setState("MD");
        address3.setZipCode("21654");

        Company company3 = new Company();
        company3.setAddress(address3);
        company3.setCompanyStatus(CompanyStatus.ACTIVE);
        company3.setId(1L);
        company3.setInsertDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company3.setInsertUserId(1L);
        company3.setIsDeleted(true);
        company3.setLastUpdateDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        company3.setLastUpdateUserId(1L);
        company3.setPhone("6625550144");
        company3.setTitle("Dr");
        company3.setWebsite("Website");
        when(mapperUtil.convert(Mockito.<Object>any(), Mockito.<Company>any())).thenReturn(company3);
        companyServiceImpl.update(new CompanyDto());
        verify(companyRepository).save(Mockito.<Company>any());
        verify(companyRepository).findById(Mockito.<Long>any());
        verify(mapperUtil).convert(Mockito.<Object>any(), Mockito.<Company>any());
    }
}

