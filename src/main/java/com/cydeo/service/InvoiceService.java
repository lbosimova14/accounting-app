package com.cydeo.service;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {
    InvoiceDto findInvoiceById(Long id);

    List<InvoiceDto> getAllInvoicesOfCompany(InvoiceType invoiceType) throws Exception;

    BigDecimal getTotalPriceOfInvoice(Long id);
    BigDecimal getTotalTaxOfInvoice(Long id);

    InvoiceDto update(Long id, InvoiceDto invoiceDto);

    void approve(Long invoiceId);

    void delete(Long invoiceId);

    InvoiceDto printInvoice(Long id);

    InvoiceDto getNewInvoice(InvoiceType invoiceType) throws Exception;

    InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType);
}
