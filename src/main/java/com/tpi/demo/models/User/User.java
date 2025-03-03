package com.tpi.demo.models.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Field;



@Collation("Account") // Custom Collaction for User Class
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id // Identifier for the document
    private String ID;

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



}
