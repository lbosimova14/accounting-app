package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(unique = true)
   private String username;
    private String password ;
    private String firstname ;
    private String lastname;
    private String phone;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Company company;

}
