package com.cydeo.converter;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.service.ClientVendorService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDtoConverter implements Converter<String, ClientVendorDto> {


    private final ClientVendorService clientVendorService;

    //@Lazy annotation will help this error : Relying upon circular references
    public ClientVendorDtoConverter(@Lazy ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @SneakyThrows
    @Override
    public ClientVendorDto convert(String id) {
        return clientVendorService.findClientVendorById(Long.parseLong(id));
    }

}
