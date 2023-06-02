package com.cydeo.service.implementation;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorImpl implements ClientVendorService {

   private final ClientVendorRepository clientVendorRepository;
    private  final MapperUtil mapperUtil;

    public ClientVendorImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
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
//
//      List<ClientVendor> clientVendorList = clientVendorRepository.findAllClientVendorByClientVendorType();
//      return clientVendorList.stream().map(obj -> mapperUtil.convert(obj, new ClientVendorDto())).collect(Collectors.toList());
//return  null;
    }

    @Override
    public ClientVendorDto save(ClientVendorDto clientVendorDto) {
//        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        ClientVendor clientVendor = clientVendorRepository.save(mapperUtil.convert(clientVendorDto, new ClientVendor()));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }
}
