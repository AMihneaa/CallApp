package com.tpi.demo.service;

import com.mongodb.MongoException;
import com.tpi.demo.models.Enums.ReservationType;
import com.tpi.demo.models.Point.StopPoint;
import com.tpi.demo.models.Reservation.Reservation;
import com.tpi.demo.models.Reservation.ReservationRepository;
import com.tpi.demo.models.Route.Route;
import com.tpi.demo.models.Route.RouteRepository;
import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationService {

    private  final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private  RouteService routeService;
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RouteService routeService, RouteRepository routeRepository){
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    public void createReservation(String routeID, String email, String reservation){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new MongoException("user email is incorrect!")
        );

        String userID = user.getId();

        Route route = routeService.findByID(routeID);

        routeService.checkAvailableSeats(route);
        routeService.updateSeats(routeID);

        Reservation res;
        switch (reservation){
            case "ROUTE":
                res = new Reservation(userID, route.getId(), route.getTransportType(), ReservationType.ROUTE);
                break;
            case "DAILY":
                res = new Reservation(userID, route.getId(), route.getTransportType(), ReservationType.DAILY);
                break;
            case "MONTHLY":
                res = new Reservation(userID, route.getId(), route.getTransportType(), ReservationType.MONTHLY);
                break;
            default:
                throw new RuntimeException("Reservation Type is invalid");
        }

        reservationRepository.save(res);
    }

    public  Map<String, Map<String, Object>> getAllRouteID(String username){
        Optional<User> user = userRepository.findByEmail(username);

        if (!user.isPresent()){
            throw new RuntimeException("User is invalid!");
        }

        Optional<List<Reservation>> res = reservationRepository.findAllByUserId(user.get().getId());

        if (!res.isPresent()){
            throw new RuntimeException("User doesn't have a reservation!");
        }

        Map<String, Map<String, Object>> reservationMap = new LinkedHashMap<>();

        int counter = 1;
        for (Reservation r : res.get()) {
            Route route = routeRepository.findById(r.getRouteId()).orElse(null);
            if (route == null) continue;

            List<String> stopPoints = route.getStops()
                    .stream()
                    .map(StopPoint::getLocation)
                    .toList();

            Map<String, Object> inner = new LinkedHashMap<>();
            inner.put("Reservation Status", r.getStatus());
            inner.put("Routes", stopPoints);

            reservationMap.put(String.valueOf(counter++), Map.of(
                    r.getTransportType().toString(), inner
            ));
        }

        return reservationMap;
    }

}
