package com.cydeo.converter;

import com.cydeo.dto.RoleDto;
import com.cydeo.service.RoleService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * This class we need for thymeleaf, if you work with angular then we dont need it
 * When this class methods run? When we use @ConfigurationPropertiesBinding annotation Spring will run automatically
 */

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDto> {

    private final RoleService roleService;

    public RoleDtoConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * We need to convert String (th:value="${role.id}" value is "1", "2") to object
     * @param id the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return
     */
    @SneakyThrows
    @Override
    public RoleDto convert(String id) {
        return roleService.findRoleById(Long.parseLong(id));
    }

}