package com.cydeo.repository;

import com.cydeo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressById(Long id);
    @Query(value = "Select  a.country FROM Address a")
    List<Address> fetchAllCountries ();
//    List<Address> findAllAddressByCountry();


}
