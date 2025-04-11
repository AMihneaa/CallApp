package com.tpi.demo.mapper;

import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneDTO;
import com.tpi.demo.models.Reservation.Reservation;
import com.tpi.demo.models.Reservation.ReservationDTO;
import org.springframework.stereotype.Component;

@Component
public class TransportMapper {

    public AirplaneDTO modelToDTO(Airplane airplane){
        return new AirplaneDTO(airplane.getModel(), airplane.getSeatingCapacity(), airplane.getAirline(), airplane.getStatus());
    }

}
