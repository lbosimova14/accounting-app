package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
//userRepository.listAll(); //SELECT * FROM USERS where is_deleted=false
//Whatever repository created with User Entity, this query concatenate automatically with 'where' clause
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;
    @ManyToOne // fetch = FetchType.LAZY: Fetch when needed, EAGER: Fetch immediately
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
