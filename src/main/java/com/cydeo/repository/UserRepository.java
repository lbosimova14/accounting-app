package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Derived Queries
    User findUserById(Long id);

    @Transactional
    User findByUsername(String username);

    List<User> findAllByRoleDescription(String roleDescription);

    List<User> findAllByCompanyTitle(String companyTitle);

    List<User> findAllByCompanyTitleAndRoleDescription(String companyTitle, String role);


}
