package com.lemakhno.shopping.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lemakhno.shopping.entities.UserEntity;
import com.lemakhno.shopping.models.UserRegistration;

public interface UserService extends UserDetailsService {
    
    public UserEntity save(UserRegistration userRegistrationDto);
}
