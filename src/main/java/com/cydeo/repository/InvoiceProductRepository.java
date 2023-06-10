package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    InvoiceProduct findInvoiceProductById(Long id);

    List<InvoiceProduct> findAllByInvoice(Invoice invoice);


    List<InvoiceProduct> findAllByInvoice_Id(Long invoiceId);

    List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(InvoiceType type, Product product, Integer remainingQuantity);

    List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_Company(InvoiceStatus invoiceStatus, Company company);

    List<InvoiceProduct> findAllByInvoice_InvoiceTypeAndInvoice_Company(InvoiceType invoiceType, Company company);
}
