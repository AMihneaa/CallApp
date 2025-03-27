package com.tpi.demo.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String userName;
    private String email;
    private String password;
}
