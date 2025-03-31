package com.tpi.demo.service;

import com.tpi.demo.models.Route.Route;
import com.tpi.demo.models.Route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    public List<Route> findAllRouteByArrivalAndDestination(String name){
        return routeRepository.findByStopsLocation(name);
    }

}
