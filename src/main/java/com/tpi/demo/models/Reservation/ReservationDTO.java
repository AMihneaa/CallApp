package com.tpi.demo.models.Reservation;

import com.tpi.demo.models.Enums.ReservationType;
import com.tpi.demo.models.Enums.TransportType;
import com.tpi.demo.models.Point.StopPoint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

    private StopPoint departureLocation;

    private StopPoint arrivalLocation;

    private TransportType transportType;

    private ReservationType reservationType;

    public ReservationDTO( StopPoint departureLocation, StopPoint arrivalLocation, TransportType transportType, ReservationType reservationType) {
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
        this.transportType = transportType;
        this.reservationType = reservationType;
    }
}
