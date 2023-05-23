package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findByUsername(String username);

//    findAllByRole_Description(
       List<User> findAllByRoleDescription(String roleDescription);

    List<User> findAllByCompanyTitle(String companyTitle);
    List<User> findAllByCompanyTitleAndRoleDescription(String companyTitle, String role);

}
