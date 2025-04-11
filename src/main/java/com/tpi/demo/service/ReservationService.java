package com.tpi.demo.service;

import com.mongodb.MongoException;
import com.tpi.demo.models.Enums.ReservationType;
import com.tpi.demo.models.Reservation.Reservation;
import com.tpi.demo.models.Reservation.ReservationRepository;
import com.tpi.demo.models.Route.Route;
import com.tpi.demo.models.User.User;
import com.tpi.demo.models.User.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private  final UserRepository userRepository;
    private  RouteService routeService;
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RouteService routeService){
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.routeService = routeService;
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

}
