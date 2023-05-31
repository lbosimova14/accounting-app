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
//            model.addAttribute("newUser", new UserDto());
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
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", companyService.listAllCompany());
        return "/user/user-update";
    }

    @PostMapping("/update/{userId}")
        public String updateUser(@Valid @ModelAttribute("userId") UserDto userDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "/user/user-update";
        }

        userService.update(userDto);
        return "redirect:/users/list";
    }





}
