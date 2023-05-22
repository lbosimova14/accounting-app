package com.cydeo.service.implementation;

import com.cydeo.dto.AddressDto;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public AddressDto findAddressById(Long id) {
        return mapperUtil.convert(addressRepository.findAddressById(id), new AddressDto());
    }


}
