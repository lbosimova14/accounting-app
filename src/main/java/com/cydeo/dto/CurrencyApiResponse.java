package com.cydeo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
//ignoreUnknown = true is related to security, if somebody try to put new field in this class, ignore it, dont put. anything outside the field not allowed

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyApiResponse {

    private LocalDate date;
    private Usd usd;

    @Data
    public static class Usd {
        private BigDecimal eur;
        private BigDecimal gbp;
        private BigDecimal inr;
        private BigDecimal jpy;
        private BigDecimal cad;

    }
}
