package com.tpi.demo.models.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.*;
import java.util.List;

@Repository
public class RouteRepositoryImpl implements RouteRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RouteRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Route> findByStopsLocation(String location) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stops.location").is(location));
        List<Route> routes =  mongoTemplate.find(query, Route.class);

        if (routes.isEmpty()){
            System.out.println("No routes found for the given location " + location);
            return List.of();
        }

        return routes;
    }
}
