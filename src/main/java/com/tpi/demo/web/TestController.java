package com.tpi.demo.web;

import com.tpi.demo.models.Route.Route;
import com.tpi.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/")
    public String test() {
        return "Server is running and u on";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userTest(){
        return "User Page";
    }

    @GetMapping("/user/{stopPointName}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Route> findAllByStopPoints(@PathVariable String stopPointName){
        List<Route> routes = routeService.findAllRouteByArrivalAndDestination(stopPointName);

        return routes;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminTest(){
        return "Admin Page";
    }
}
