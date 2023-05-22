package com.cydeo.service;

import com.cydeo.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * UserDetailsService interface that spring security will understand, one more layer to conversion from userRepository
 * DB to userService or UI.
 */
public interface SecurityService extends UserDetailsService {

    UserDto getLoggedInUser();

}