package com.tpi.demo.service;

import com.tpi.demo.models.User.User;
import com.tpi.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

/*
    Implement UserDetailsService interface from Spring Security
    !! Required Method:  loadUserByUsername  !!
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;


    /*
        Email as parameter
        Find User in DB
        If User exists, converts the User Model into a UserDetails object that Spring Security can understand
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Search for the User in db
        // Return User or throw an error
        User user = userRepository.findByEmail(email).
                orElseThrow(
                        () -> new UsernameNotFoundException("User not exists!")
                );

        // Convert User to UserDetails Object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Pass Username
                user.getPassword(), // Pass Password
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Default Role
        );
    }
}
