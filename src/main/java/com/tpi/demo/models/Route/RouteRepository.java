package com.tpi.demo.models.Route;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RouteRepository extends MongoRepository<Route, String>, RouteRepositoryCustom {
    Optional<Route> findById(String id);
    boolean existsByTransportIdAndStops_DepartureTime(String transportId, LocalDateTime departureTime);
}
