package com.cydeo.entity;

import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity{

      private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
}
