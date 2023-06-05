package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

   private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String navigateToCompanyList(Model model){
        model.addAttribute("companies", companyService.listAllCompany());
        return "company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model){
        model.addAttribute("newCompany", new CompanyDto());

        return "company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@Valid @ModelAttribute("newCompany") CompanyDto companyDto, BindingResult bindingResult, Model model){

//if any broken data coming , then somehow stop the post method
        if (bindingResult.hasErrors()) {
            //stays in the same page
            return "company/company-create";
        }
        companyService.save(companyDto);
        return "redirect:/companies/list";
    }

    @GetMapping("/update/{companyId}")
    public String editCompany(@PathVariable("companyId") Long companyId, Model model){
        model.addAttribute("company", companyService.findCompanyById(companyId));
        return "/company/company-update";
    }
// id is get read from here private Long id; in CompanyDTO class, path parameter and id field is the same
    @PostMapping("/update/{id}")
    public String updateCompany(@ModelAttribute("company") CompanyDto companyDto, BindingResult bindingResult){
        boolean isThisCompanyTitle = companyDto.getTitle().equals(companyService.findCompanyById(companyDto.getId()).getTitle());
        if (companyService.isTitleExist(companyDto.getTitle()) && !isThisCompanyTitle) {
            bindingResult.rejectValue("title", " ", "This title already exists.");
        }

        if (bindingResult.hasErrors()) {
            companyDto.setId(companyDto.getId());
            return "/company/company-update";
        }

        companyService.update(companyDto);
        return "redirect:/companies/list"; ///companies/list is end point, not html file
    }

    @GetMapping("/activate/{companyId}")
    public  String setToActivate(@PathVariable("companyId") Long companyId){
        companyService.activate(companyId);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{companyId}")
    public  String setToDeactivate(@PathVariable("companyId") Long companyId){
        companyService.deactivate(companyId);
        return "redirect:/companies/list";
    }



}
