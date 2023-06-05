package com.cydeo.service;

import com.cydeo.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    //the method name not important to match to repository method.
    UserDto findByUsername(String username); //return to DTO, bc controller call this method

    UserDto findUserById(Long id);

    //    List<UserDto> findAllUsers();
//The provider company "CYDEO" (ID=1) should not be listed on the table
    List<UserDto> getFilteredUsers();

//    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    void save(UserDto userDto);

    void delete(Long userID);

    //this method deletes the data from database too, bc implementation does not have isDeleted flag
    @Transactional
    //when you write delete, you need add @Transaction, when you try to delete, in database you need to create new transaction, it is related for roll back
//@Transactional you need to use for Drive queries, if you are building @Query jpql or native query then user @Modifying.
    void deleteByUserId(Long userId);

    Boolean emailExist(UserDto userDto);
}
