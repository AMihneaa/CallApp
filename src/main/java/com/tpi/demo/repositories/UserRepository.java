package com.tpi.demo.repositories;

import com.tpi.demo.models.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    Interface for User Model
    Connection between User Model and Database
    Provide CRUD operation for the User model
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

}
