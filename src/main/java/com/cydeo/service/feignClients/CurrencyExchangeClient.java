package com.cydeo.service.feignClients;


//import com.cydeo.accountingsimplified.dto.CurrencyApiResponse;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "currency-api", url = "${currency.api.url}")
public interface CurrencyExchangeClient {

//    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
//    CurrencyApiResponse getUsdBasedCurrencies();
}
