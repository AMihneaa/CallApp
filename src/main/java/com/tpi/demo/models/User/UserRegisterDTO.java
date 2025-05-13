package com.tpi.demo.models.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegisterDTO {
    private String userName;
    private String email;
    private String password;

    public UserRegisterDTO(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
