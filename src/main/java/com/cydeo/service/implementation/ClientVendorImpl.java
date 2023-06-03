package com.cydeo.service.implementation;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorImpl implements ClientVendorService {

   private final ClientVendorRepository clientVendorRepository;
    private  final MapperUtil mapperUtil;

    private final SecurityService securityService;

    public ClientVendorImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public ClientVendorDto findClientVendorById(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findClientVendorById(id);
        return mapperUtil.convert(clientVendor, new ClientVendorDto());

    }

    @Override
    public List<ClientVendorDto> listAllClientsAndVendors() {
        List<ClientVendor> clientVendors = clientVendorRepository.findAll();
        return  clientVendors.stream().map( obj -> mapperUtil.convert(obj , new ClientVendorDto())).collect(Collectors.toList());

    }

    @Override
    public List<ClientVendorDto> findClientVendorTypes() {

        List<String> clientVendorList = clientVendorRepository.fetchAllClientVendorType();
        return clientVendorList.stream().map(obj -> mapperUtil.convert(obj, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto save(ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor clientVendor = clientVendorRepository.save(mapperUtil.convert(clientVendorDto, new ClientVendor()));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public void update(ClientVendorDto clientVendorDto) {

    Optional<ClientVendor> retrievedClientVendor = clientVendorRepository.findById(clientVendorDto.getId());
    clientVendorDto.getAddress().setId(retrievedClientVendor.get().getAddress().getId());     // otherwise it creates new address instead of updating existing one
     clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
       ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(convertedClientVendor);

    }

    @Override
    public void delete(Long clientVendorID) {
        ClientVendor clientVendor = clientVendorRepository.findClientVendorById(clientVendorID);
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);
    }


}
