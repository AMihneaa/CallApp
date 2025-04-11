package com.tpi.demo.models.Route;

import java.util.List;

public interface RouteRepositoryCustom {
    List<Route> findByStopsLocation(String location);
    List<Route> findByDepartureAndArrivalLocation(String departureLocation, String arrivalLocation);
    List<Route> findRestRoute(List<String> routesID, String departureLocation, String arrivalLocation);
}
