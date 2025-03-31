package com.tpi.demo.service;

import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Implement UserDetailsService interface from Spring Security
    !! Required Method:  loadUserByUsername  !!
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

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


        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .filter(role -> role != null)
                .flatMap(role -> {
                    if (role.getPrivilege() == null || role.getPrivilege().isEmpty()) {
                        logger.warn("Role {} has no privileges assigned", role.getName());
                    }
                    Stream<SimpleGrantedAuthority> roleStream = Stream.of(
                            new SimpleGrantedAuthority(role.getName())
                    );
                    Stream<SimpleGrantedAuthority> privilegeStream = role.getPrivilege().stream()
                            .map(privilege -> new SimpleGrantedAuthority(privilege.getName()));
                    return Stream.concat(roleStream, privilegeStream);
                })
                .collect(Collectors.toSet());

        if (authorities.isEmpty()) {
            logger.error("User has no authorities assigned");
            throw new UsernameNotFoundException("User has no authorities assigned");
        }
        // Convert User to UserDetails Object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Pass Username
                user.getPassword(), // Pass Password
                authorities
        );
    }
}
