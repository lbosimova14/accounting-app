package com.cydeo.service;

import com.cydeo.dto.AddressDto;

import java.util.List;


public interface AddressService {

    AddressDto findAddressById(Long id);

    List<AddressDto> findAddressByCountry();

    List<String> getCountryList();


}
