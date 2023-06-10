package com.cydeo.service.feignClients;


//import com.cydeo.accountingsimplified.dto.CurrencyApiResponse;

import com.cydeo.dto.CurrencyApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "currency-api", url = "${currency.api.url}")
public interface CurrencyExchangeClient {

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    CurrencyApiResponse getUsdBasedCurrencies();
}
