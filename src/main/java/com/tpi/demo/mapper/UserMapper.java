package com.tpi.demo.mapper;

import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserDTO;
import com.tpi.demo.models.User.UserRegisterDTO;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User registerUserToDTO(UserRegisterDTO userRegisterDTO){
        return new User(userRegisterDTO.getUserName(), userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
    }

    public UserDTO userToDTO(User user){
        return new UserDTO(user.getEmail(), user.getRoles());
    }

    public User dtoToUser(UserDTO userDTO){
        return new User(userDTO.getEmail(), userDTO.getRoles());
    }

}
