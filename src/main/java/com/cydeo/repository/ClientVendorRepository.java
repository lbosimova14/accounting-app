package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    ClientVendor findClientVendorById(Long id);
      @Query(value = "Select cvt.clientVendorType  FROM ClientVendor cvt")
       List<String> fetchAllClientVendorType ();


}
