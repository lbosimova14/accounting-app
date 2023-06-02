package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {
    ClientVendorDto findClientVendorById(Long id);

    List<ClientVendorDto> listAllClientsAndVendors();
}
