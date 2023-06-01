package com.cydeo.service.implementation;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
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
        User user = userRepository.findByUsername(username); //Select username FROM USERS where is_deleted=false
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
                //.map means iterate each object
                .map(entity -> {
                    UserDto dto = mapperUtil.convert(entity, new UserDto());
                    dto.setIsOnlyAdmin(checkIfOnlyAdminForCompany(dto));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) {
        //save method is equal to update, there are not update method in crud
        //find current user
       User currentUser= userRepository.findByUsername(userDto.getUsername());
     User updatedUser=  mapperUtil.convert(userDto, new User());
     //capture the existing user ID then set back the same id, if you dont retrieve from database, ui does not have id, then it will create new id, which we dont want
        updatedUser.setId(currentUser.getId());
        updatedUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
     userRepository.save(updatedUser);

        return findByUsername(userDto.getUsername());
    }

    @Override
    public void save(UserDto userDto) {
//        get the new Dto user from view and convert to Entity then save it to databse
        User user = userRepository.save(mapperUtil.convert(userDto, new User()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void delete(Long userID) {
        User user = userRepository.findUserById(userID);
        //dont delete data in database, change the flag to true only, it only deleted in ui, but data is kept in database
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId());
        userRepository.save(user);


    }

    @Override
    public void deleteByUserId(Long userID) {
        userRepository.deleteById(findUserById(userID).getId()); //getId() is returning as a optional
    }

    @Override
    public Boolean emailExist(UserDto userDto) {
        User userWithUpdatedEmail = userRepository.findByUsername(userDto.getUsername());
        if (userWithUpdatedEmail == null) return false;
        return !userWithUpdatedEmail.getId().equals(userDto.getId());
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
