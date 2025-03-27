package com.tpi.demo.models.User;

import com.tpi.demo.models.Role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String email;
    private Set<Role> roles = new HashSet<>();

    public UserDTO(){}

    public UserDTO(String email){
        this.email = email;
    }

    public UserDTO(String email, Set<Role> roles){
        this.email = email;
        this.roles = roles;
    }
}
