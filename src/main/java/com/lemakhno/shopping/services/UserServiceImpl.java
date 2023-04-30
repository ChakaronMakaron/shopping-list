package com.lemakhno.shopping.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lemakhno.shopping.entities.RoleEntity;
import com.lemakhno.shopping.entities.UserEntity;
import com.lemakhno.shopping.models.UserRegistration;
import com.lemakhno.shopping.repositories.RoleRepository;
import com.lemakhno.shopping.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Long ROLE_USER_ID = 1L;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
        }
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public UserEntity save(UserRegistration userRegistrationDto) {
        RoleEntity roleUser = roleRepository.findById(ROLE_USER_ID).get();
        UserEntity user = new UserEntity(
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(),
                passwordEncoder.encode(userRegistrationDto.getPassword()),
                Arrays.asList(roleUser)
        );

        try {
            // Possible violation of 'Unique email constraint' in DB table 'user'
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }
    
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
