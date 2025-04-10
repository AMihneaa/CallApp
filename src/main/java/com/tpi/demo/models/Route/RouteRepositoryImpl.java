package com.tpi.demo.models.Route;

import com.tpi.demo.models.Point.StopPoint;
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
        System.out.println("Executing query: " + query);

        List<Route> routes = mongoTemplate.find(query, Route.class);
        System.out.println("Query result: " + routes);

        if (routes.isEmpty()){
            System.out.println("No routes found for the given location " + location);
            return List.of();
        }

        return routes;
    }

    @Override
    public List<Route> findByDepartureAndArrivalLocation(String departureLocation, String arrivalLocation) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stops.location").in(departureLocation, arrivalLocation));
        System.out.println("Execution query: " + query);

        List<Route> allRoutes = mongoTemplate.find(query, Route.class);
        System.out.println("Raw query result: " + allRoutes);

        List<Route> matchingRoutes = allRoutes.stream()
                .filter(route -> {
                    List<String> locations = route.getStops().stream()
                            .map(StopPoint::getLocation)
                            .map(String::toLowerCase)
                            .toList();
                    int idxDeparture = locations.indexOf(departureLocation.toLowerCase());
                    int idxArrival = locations.indexOf(arrivalLocation.toLowerCase());
                    return idxDeparture != -1 && idxArrival != -1 && idxDeparture < idxArrival;
                })
                .toList();

        if (matchingRoutes.isEmpty()) {
            System.out.println("No matching routes found with correct order.");
            return List.of();
        }

        return matchingRoutes;
    }

    @Override
    public List<Route> findRestRoute(List<String> routesID, String departureLocation, String arrivalLocation) {
        Query query = new Query();
        System.out.println(routesID);

        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("_id").nin(routesID),
                        new Criteria().orOperator(
                                Criteria.where("stops.location").regex("^" + departureLocation + "$", "i"),
                                Criteria.where("stops.location").regex("^" + arrivalLocation + "$", "i")
                        )
                )
        );

        System.out.println("Executing query: " + query);

        List<Route> routes = mongoTemplate.find(query, Route.class);
        System.out.println("Query result: " + routes);

        if (routes.isEmpty()) {
            System.out.println("No routes found excluding the given IDs and matching the locations.");
            return List.of();
        }

        return routes;
    }

}
