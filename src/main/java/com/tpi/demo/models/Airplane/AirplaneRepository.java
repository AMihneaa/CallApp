package com.tpi.demo.models.Airplane;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AirplaneRepository extends MongoRepository<Airplane, String> {
    Optional<Airplane> findAirplaneByModel(String model);
}
