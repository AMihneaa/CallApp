package com.tpi.demo.service;

import com.tpi.demo.mapper.UserMapper;
import com.tpi.demo.models.Role.Role;
import com.tpi.demo.models.Role.RoleRepository;
import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRegisterDTO;
import com.tpi.demo.models.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public void registerUser(UserRegisterDTO dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists!");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(
                        () -> new RuntimeException("Default Role not found!")
                );

        User user = userMapper.registerUserToDTO(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
    }

}
