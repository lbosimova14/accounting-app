package com.cydeo.service;

import com.cydeo.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);

    UserDto findUserById(Long id);

//    List<UserDto> findAllUsers();
//The provider company "CYDEO" (ID=1) should not be listed on the table
    List<UserDto> getFilteredUsers();

//    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    void save(UserDto userDto);
}
