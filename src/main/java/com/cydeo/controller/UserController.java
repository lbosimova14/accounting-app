package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

   private final UserService userService;
   private final RoleService roleService;
   private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }



    @GetMapping("/list")
    public String listUsers(Model model){

        model.addAttribute("users", userService.getFilteredUsers());
        return "user/user-list";
    }

    @GetMapping("/create")
    public String navigateToUserCreate(Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.getFilteredRolesForCurrentUser());
        model.addAttribute("companies", companyService.getFilteredCompaniesForCurrentUser());
        return "/user/user-create";
    }


    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("newUser") UserDto userDto, BindingResult bindingResult,Model model ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoles",  roleService.getFilteredRolesForCurrentUser());
            model.addAttribute("companies",companyService.getFilteredCompaniesForCurrentUser());
            return "/user/user-create";
        }
        userService.save(userDto);
        return "redirect:/users/list";
    }

    @GetMapping("/update/{userId}")
    public String editUser(@PathVariable("userId") Long userId, Model model){
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("userRoles",  roleService.getFilteredRolesForCurrentUser());
        model.addAttribute("companies",companyService.getFilteredCompaniesForCurrentUser());
        return "/user/user-update";
    }

    @PostMapping("/update/{userId}")
        public String updateUser(@PathVariable("userId") Long userId,@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){

        userDto.setId(userId);  // spring cannot set id since it is not seen in UI and we need to check if updated email is used by different user.
        boolean emailExist = userService.emailExist(userDto);

        if (bindingResult.hasErrors() || emailExist){
            if (emailExist) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }

            model.addAttribute("companies", companyService.getFilteredCompaniesForCurrentUser());
            model.addAttribute("userRoles", roleService.getFilteredRolesForCurrentUser());
            return "/user/user-update";
        }

        userService.update(userDto);
        return "redirect:/users/list";
    }


    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userID){
        userService.delete(userID);
        return "redirect:/users/list";
    }




}
