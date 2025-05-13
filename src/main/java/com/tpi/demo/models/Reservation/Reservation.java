package com.tpi.demo.models.Reservation;

import com.tpi.demo.models.Enums.Status;
import com.tpi.demo.models.Enums.ReservationType;
import com.tpi.demo.models.Enums.TransportType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reservations")
@Getter
@Setter
public class Reservation {

    @Id
    private String id;

    private String userId;

    private String routeId;

    private TransportType transportType;

    private ReservationType reservationType;

    private Status status = Status.INACTIVE;

    public Reservation(String userId, String routeId, TransportType transportType, ReservationType reservationType){
        this.userId = userId;
        this.routeId = routeId;
        this.transportType = transportType;
        this.reservationType = reservationType;
    }
}
