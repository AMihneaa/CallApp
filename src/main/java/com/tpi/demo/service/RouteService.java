package com.tpi.demo.service;

import com.tpi.demo.models.Point.StopPoint;
import com.tpi.demo.models.Route.Route;
import com.tpi.demo.models.Route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    public List<Route> findAllRouteByArrivalAndDestination(String name){
        return routeRepository.findByStopsLocation(name);
    }

    public Route getRoute(String id){
        return routeRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Route not found")
                );
    }

    public void checkAvailableSeats(Route route){
        if (route.getAvailableSeats() <= 0){
            throw new IllegalStateException("No available seats for this route");
        }
    }

    public List<List<Route>> findRouteOptions(String departureLocation, String arrivalLocation){
        List<Route> directRoutes = routeRepository.findByDepartureAndArrivalLocation(departureLocation, arrivalLocation);

        List<String> directRouteIds = directRoutes.stream()
                .map(Route::getId)
                .collect(Collectors.toList());
        List<Route> restRoutes = routeRepository.findRestRoute(directRouteIds, departureLocation, arrivalLocation);

        List<List<Route>> possibleRoutes = new ArrayList<>();

        for (Route direct : directRoutes) {
            possibleRoutes.add(List.of(direct));
        }

        for (Route r1 : restRoutes) {
            for (Route r2 : restRoutes) {
                if (r1.equals(r2)) continue;

                List<String> r1Stops = r1.getStops().stream().map(s -> s.getLocation().toLowerCase()).toList();
                List<String> r2Stops = r2.getStops().stream().map(s -> s.getLocation().toLowerCase()).toList();

                if (!r1Stops.contains(departureLocation.toLowerCase()) || !r2Stops.contains(arrivalLocation.toLowerCase())) {
                    continue;
                }

                for (String shared : r1Stops) {
                    if (r2Stops.contains(shared)) {
                        int idxDep = r1Stops.indexOf(departureLocation.toLowerCase());
                        int idxSharedInR1 = r1Stops.indexOf(shared);
                        int idxSharedInR2 = r2Stops.indexOf(shared);
                        int idxArr = r2Stops.indexOf(arrivalLocation.toLowerCase());

                        if (idxDep >= 0 && idxSharedInR1 > idxDep && idxSharedInR2 >= 0 && idxArr > idxSharedInR2) {
                            possibleRoutes.add(List.of(r1, r2));
                        }
                    }
                }
            }
        }

        if (possibleRoutes.isEmpty()){
            throw new RuntimeException("No routes found!");
        }

        return possibleRoutes;
    }

}
