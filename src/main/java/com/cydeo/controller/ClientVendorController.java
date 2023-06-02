package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.AddressService;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private  final ClientVendorService clientVendorService;

    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService, AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String listAllClientsAndVendors(Model model){
        model.addAttribute("clientVendors", clientVendorService.listAllClientsAndVendors());
        return "/clientVendor/clientVendor-list";
    }


    @GetMapping("/create")
    public String navigateToClientVendorCreate(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String createClientVendor(@ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
        }

    @GetMapping("/update/{clientVendorId}")
    public String editClientVendor(@PathVariable("clientVendorId") Long clientVendorId, Model model){

        model.addAttribute("clientVendor", clientVendorService.findClientVendorById(clientVendorId));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries",addressService.getCountryList());
        return "/clientVendor/clientVendor-update";
    }




}
