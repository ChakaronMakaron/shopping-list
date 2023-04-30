package com.lemakhno.shopping.controllers;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lemakhno.shopping.constants.Endpoints;
import com.lemakhno.shopping.constants.Views;
import com.lemakhno.shopping.entities.UserEntity;
import com.lemakhno.shopping.models.UserRegistration;
import com.lemakhno.shopping.services.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final String SUCCESSFUL_REGISTRATION = "redirect:/loginPage?successfulRegistration";
    private final String REGISTRATION_FAIL = "redirect:/registration?registrationFail";
    
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(Endpoints.REGISTRATION)
    public String showRegistrationForm(Model model) {
        UserRegistration userRegistrationDto = new UserRegistration();
        model.addAttribute("user", userRegistrationDto);
        return Views.REGISTRATION;
    }

    @PostMapping(Endpoints.REGISTRATION)
    public String registerUserAccount(@ModelAttribute("user") UserRegistration registrationDto) {
        UserEntity newUser = userService.save(registrationDto);
        return isNull(newUser) ? REGISTRATION_FAIL : SUCCESSFUL_REGISTRATION;
    }
}
