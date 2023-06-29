package com.cydeo.service.feignClients;


import com.cydeo.dto.addressApi.Country;
import com.cydeo.dto.addressApi.State;
import com.cydeo.dto.addressApi.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

//package name can be microservice or proxy
@FeignClient(name = "address-api", url = "${address.api.url}")
//in the microservice we will use name = "address-api". The name is identifier in microservices
public interface AddressFeignClient {

    @GetMapping(value = "/getaccesstoken", consumes = MediaType.APPLICATION_JSON_VALUE)
    TokenDto auth(@RequestHeader("user-email") String email, @RequestHeader("api-token") String apiToken);

    //When I call localhost:8080/countries, Spring will concatenate with base url https://www.universal-tutorial.com/api/countries then returns to Country Pojo class
    @GetMapping(value = "/countries", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Country> getCountryList(@RequestHeader("Authorization") String bearerToken);

    @GetMapping(value = "/states/{country}", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<State> getStateList(@RequestHeader("Authorization") String bearerToken, @PathVariable String country);

    @GetMapping(value = "/cities/{state}", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<State> getCityList(@RequestHeader("Authorization") String bearerToken, @PathVariable String state);
}
