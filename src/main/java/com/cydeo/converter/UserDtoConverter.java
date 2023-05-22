package com.cydeo.converter;

import com.cydeo.dto.UserDto;
import com.cydeo.service.UserService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDto> {

    private final UserService userService;

    public UserDtoConverter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDto convert(String id) {
        return userService.findUserById(Long.parseLong(id));
    }

}