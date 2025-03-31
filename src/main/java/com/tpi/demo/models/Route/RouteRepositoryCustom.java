package com.tpi.demo.models.Route;

import java.util.List;

public interface RouteRepositoryCustom {
    List<Route> findByStopsLocation(String location);
}
