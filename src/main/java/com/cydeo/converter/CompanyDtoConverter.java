package com.cydeo.converter;

import com.cydeo.dto.CompanyDto;
import com.cydeo.service.CompanyService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(@Lazy CompanyService companyService) {
        this.companyService = companyService;
    }

    @SneakyThrows
    @Override
    public CompanyDto convert(String id) {
        return companyService.findCompanyById(Long.parseLong(id));
    }

}