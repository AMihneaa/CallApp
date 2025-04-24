package com.tpi.demo.web;

import com.tpi.demo.config.JWTUtil;
import com.tpi.demo.models.User.UserLoginDTO;
import com.tpi.demo.models.User.UserRegisterDTO;
import com.tpi.demo.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO){
        try {
            userService.registerUser(userRegisterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }catch (RuntimeException rte){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rte.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtUtil.generateToken(request.getUsername());

            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateUser(Authentication auth){
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid TOKEN!");
        }

        return ResponseEntity.ok(Map.of(
                "user", auth.getName(),
                "authorities", auth.getAuthorities()
        ));
    }

}
