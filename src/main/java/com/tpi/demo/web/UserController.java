package com.tpi.demo.web;

import com.tpi.demo.models.User.UserRegisterDTO;
import com.tpi.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
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

}
