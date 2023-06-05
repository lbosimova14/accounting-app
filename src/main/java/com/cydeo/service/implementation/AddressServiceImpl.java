package com.cydeo.service.implementation;

import com.cydeo.dto.AddressDto;
import com.cydeo.dto.addressApi.Country;
import com.cydeo.dto.addressApi.TokenDto;
import com.cydeo.entity.Address;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.feignClients.AddressFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;

    private final AddressFeignClient addressFeignClient;

    /**
     * These email and usertoken provided by api website after registration
     *
     * @link :https://www.universal-tutorial.com/rest-apis/free-rest-api-for-country-state-city
     * They required as req header for only get a  bearer token for future queries
     */
    @Value("${address.api.user-email}")
    private String email;

    @Value("${address.api.api-token}")
    private String userToken;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, AddressFeignClient addressFeignClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.addressFeignClient = addressFeignClient;
    }

    @Override
    public AddressDto findAddressById(Long id) {
        return mapperUtil.convert(addressRepository.findAddressById(id), new AddressDto());
    }

    @Override
    public List<AddressDto> findAddressByCountry() {

        List<Address> retrievedAddress = addressRepository.fetchAllCountries();
//        List<Address> retrievedAddress = addressRepository.findAllAddressByCountry();
        return retrievedAddress.stream().map(obj -> mapperUtil.convert(obj, new AddressDto())).collect(Collectors.toList());

    }


    private String getBearerToken() {
        TokenDto bearerToken = addressFeignClient.auth(this.email, this.userToken);
        log.info("token retrieved for address api: " + bearerToken.getAuthToken());
        return "Bearer " + bearerToken.getAuthToken();
    }

    public List<String> getCountryList() {

        List<Country> countries = addressFeignClient.getCountryList(getBearerToken());
        log.info("Total Country size is :" + countries.size());
        return countries.stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());

    }


}
