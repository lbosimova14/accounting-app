package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {
    ClientVendorDto findClientVendorById(Long id);

    List<ClientVendorDto> listAllClientsAndVendors();

    List<ClientVendorDto> findClientVendorTypes();

    ClientVendorDto save(ClientVendorDto clientVendorDto);

    void update(ClientVendorDto Id);

    void delete(Long clientVendorID);
}
