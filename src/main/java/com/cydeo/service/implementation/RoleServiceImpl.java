package com.cydeo.service.implementation;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import com.cydeo.service.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final ModelMapper modelMapper;

    private final SecurityService securityService;


    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, ModelMapper modelMapper, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
    }

    @Override
    public RoleDto findRoleById(Long id) {
        //convert the entity/db to dto/view
        return mapperUtil.convert(roleRepository.findRoleById(id), new RoleDto());
    }

    @Override
    public List<RoleDto> listAllRoles() {

        return roleRepository.findAll().stream().map(element -> modelMapper.map(element, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> getFilteredRolesForCurrentUser() {
        UserDto user = securityService.getLoggedInUser();
        if (user.getRole().getDescription().equals("Root User")) {
            List<RoleDto> list = new ArrayList<>();
            list.add(mapperUtil.convert(roleRepository.findByDescription("Admin"), new RoleDto()));
            return list;
        } else {
            return roleRepository.findAll()
                    .stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDto()))
                    .collect(Collectors.toList());
        }
    }


}