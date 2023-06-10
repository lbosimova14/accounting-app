package com.cydeo.repository;


import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice findInvoiceById(Long id);

    List<Invoice> findInvoicesByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);

    List<Invoice> findInvoicesByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus);

    Collection<Object> findInvoicesByCompanyAndInvoiceStatusOrderByDateDesc(Company company, InvoiceStatus invoiceStatus);
}
