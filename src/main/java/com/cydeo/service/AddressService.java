package com.cydeo.service;

import com.cydeo.dto.AddressDto;


public interface AddressService {

    AddressDto findAddressById(Long id);
}
