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

    private final ClientVendorService clientVendorService;

    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService, AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String listAllClientsAndVendors(Model model) {
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
    public String createClientVendor(@ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{clientVendorId}")
    public String insertClientVendor(@PathVariable("clientVendorId") Long clientVendorId, Model model) {

        model.addAttribute("clientVendor", clientVendorService.findClientVendorById(clientVendorId));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", addressService.getCountryList());
        return "/clientVendor/clientVendor-update";
    }


    //If you dont want to use @PathVariable in @PostMapping, in method argument, then make sure {id} is match to DTO id field,
//    otherwise id always will be null
    @PostMapping("/update/{id}")
    public String updateClientVendor(@ModelAttribute("clientVendor") ClientVendorDto clientVendorDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            model.addAttribute("countries", addressService.getCountryList());
            return "/clientVendor/clientVendor-update";
        }

        clientVendorService.update(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/delete/{clientVendorId}")
    public String deleteClientVendor(@PathVariable("clientVendorId") Long clientVendorID) {
        clientVendorService.delete(clientVendorID);
        //make sure add '@Where(clause = "is_deleted=false")' ClientVendor Entity, then deleted item disappears from ui, otherwise, listAllClientsAndVendors() method retrieves all isDeleted=false and true
        return "redirect:/clientVendors/list";
    }


}
