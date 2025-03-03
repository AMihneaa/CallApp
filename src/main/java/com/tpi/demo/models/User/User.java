package com.tpi.demo.models.User;

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
    private String userName;

    @Field("email")
    private String email;

    @Field("password")
    private String password;



}
