package com.cydeo.entity;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "invoices")
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity {

    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "client_vendor_id")
    private ClientVendor clientVendor;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceProduct> invoiceProducts;
}
