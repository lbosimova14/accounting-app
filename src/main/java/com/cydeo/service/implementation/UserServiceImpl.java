package com.cydeo.service.implementation;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy SecurityService securityService, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("The user is not found for " + userId);
        }
        UserDto dto = mapperUtil.convert(user, new UserDto());
        return dto;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("The user is not found for " + username);
        }
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> getFilteredUsers() {
        List<User> userList;
        if (isCurrentUserRootUser()) {
            userList = userRepository.findAllByRoleDescription("Admin");
        } else {
            userList = userRepository.findAllByCompanyTitle(getCurrentUserCompanyTitle());
        }
        return userList.stream()
                .sorted(Comparator.comparing((User u) -> u.getCompany().getTitle()).thenComparing(u -> u.getRole().getDescription()))
                .map(entity -> {
                    UserDto dto = mapperUtil.convert(entity, new UserDto());
                    dto.setIsOnlyAdmin(checkIfOnlyAdminForCompany(dto));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) {

       User currentUser= userRepository.findByUsername(userDto.getUsername());
     User convertedUser=  mapperUtil.convert(userDto, new User());
     convertedUser.setId(currentUser.getId());
     userRepository.save(convertedUser);

        return findByUsername(userDto.getUsername());
    }

    @Override
    public void save(UserDto userDto) {
        User user = userRepository.save(mapperUtil.convert(userDto, new User()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }


    private Boolean isCurrentUserRootUser() {
        return securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user");
    }

    private String getCurrentUserCompanyTitle() {
        String currentUserName = securityService.getLoggedInUser().getUsername();
        return userRepository.findByUsername(currentUserName).getCompany().getTitle();
    }

    private Boolean checkIfOnlyAdminForCompany(UserDto dto) {
        if (dto.getRole().getDescription().equalsIgnoreCase("Admin")) {
            List<User> users = userRepository.findAllByCompanyTitleAndRoleDescription(dto.getCompany().getTitle(), "Admin");
            return users.size() == 1;
        }
        return false;
    }


}
