package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {
    ClientVendorDto findClientVendorById(Long id);

    List<ClientVendorDto> listAllClientsAndVendors();

    List<ClientVendorDto> findClientVendorTypes();

    ClientVendorDto save(ClientVendorDto clientVendorDto);

    void update(ClientVendorDto Id);

    void delete(Long clientVendorID);

    List<ClientVendorDto> getAllClientVendorsOfCompany(ClientVendorType clientVendorType);
}
