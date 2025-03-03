package com.tpi.demo.config;

import com.tpi.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*
            Configure End-Points and permissions
         */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers("/login").permitAll() // Allow unathenticated request to access /login
                        .anyRequest().authenticated() // Allow Auth Users for any other routes
        )
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .authenticationManager(authenticationManager());
        ;


        return httpSecurity.build();
    }

    /*
        Override AuthManager method
        Create a new implementation for the authentication request process
     */
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(daoAuthenticationProvider());
    }


    /*
        Override DaoAuthProvider that provide auth for users based
        on data from db
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Create a new Instance for DaoAuthProvider
        provider.setUserDetailsService(userDetailsService); // Wire new UserDetailService on the instance
        provider.setPasswordEncoder(passwordEncoder()); // Set a password encoder;

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
