package com.cydeo.entity;

import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "clients_vendors")
@Where(clause = "is_deleted=false")
public class ClientVendor extends BaseEntity {

    private String clientVendorName;
    private String phone;
    private String website;
    //    if you dont put @Enumerated, type will be integer by default, 0,1. EnumType.STRING is changes to varchar
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
