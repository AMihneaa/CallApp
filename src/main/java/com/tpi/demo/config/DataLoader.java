package com.tpi.demo.config;

import com.tpi.demo.models.User.User;
import com.tpi.demo.repositories.UserRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        User user = new User();
        user.setUserName("Test");
        user.setEmail("text@ceva");
        user.setPassword(passwordEncoder.encode("test"));
        Optional<User> test = userRepository.findByEmail("text@ceva");
        if (test.isPresent()){
            return ;
        }
        userRepository.save(user);
    }

}
