package com.lemakhno.shopping.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lemakhno.shopping.constants.Endpoints;
import com.lemakhno.shopping.constants.Views;

@Controller
public class LoginController {
    
    @GetMapping(Endpoints.LOGIN_PAGE)
    public String showLoginForm() {
        return Views.LOGIN;
    }
}
