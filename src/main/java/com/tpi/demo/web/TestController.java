package com.tpi.demo.web;

import com.tpi.demo.models.Route.Route;
import com.tpi.demo.service.ReservationService;
import com.tpi.demo.service.RouteService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class TestController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/user/{stopPointName}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Route> findAllByStopPoints(@PathVariable String stopPointName){
        List<Route> routes = routeService.findAllRouteByArrivalAndDestination(stopPointName);

        return routes;
    }

    @GetMapping("/user/{departureLocation}/to/{arrivalLocation}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<List<Route>>> findRouteByDepartureAndArrivalLocation(
            @PathVariable String departureLocation,
            @PathVariable String arrivalLocation) {
        try {
            List<List<Route>> result = routeService.findRouteOptions(departureLocation, arrivalLocation);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/user/route/{routeID}/{reservationType}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> buyTicketForARoute(
            @PathVariable String routeID,
            @PathVariable String reservationType,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        try{
            reservationService.createReservation(routeID, userDetails.getUsername(), reservationType);

            return ResponseEntity.status(HttpStatus.CREATED).body("201");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

    @GetMapping("/user/myTicket")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> myTicket(@AuthenticationPrincipal UserDetails userDetails){
        try{
            Map<String, Map<String, Object>> getAllRouteID = reservationService.getAllRouteID(userDetails.getUsername());

            return ResponseEntity.ok(getAllRouteID);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

}
