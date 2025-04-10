package com.tpi.demo.service;

import com.tpi.demo.models.Reservation.Reservation;
import com.tpi.demo.models.Reservation.ReservationDTO;
import com.tpi.demo.models.Reservation.ReservationRepository;
import com.tpi.demo.models.Route.Route;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(ReservationDTO dto){
        /*
        Verific daca mai sunt
         */
    }

}
