package com.tpi.demo.models.User;

import com.tpi.demo.models.Role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class User {

    @Id // Identifier for the document
    private String id;

    @Field("username")
    @NotBlank(message = "Username field is required!")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 20 characters")
    private String userName;

    @Field("email")
    @NotBlank(message = "Email field is required!")
    @Email(message = "Email should be valid")
    private String email;

    @Field("password")
    @NotBlank(message = "Password field si required!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Password must contain at least one letter and one number")
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String userName, String email, String password, Set<Role> roles){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
