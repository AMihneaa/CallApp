package com.tpi.demo.models.Route;

import com.tpi.demo.models.Enums.Status;
import com.tpi.demo.models.Enums.TransportType;
import com.tpi.demo.models.Point.StopPoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("routes")
@Getter
@Setter
public class Route {

    @Id
    private String id;

    private List<StopPoint> stops;

    private String transportId;

    private TransportType transportType;

    private int availableSeats;

    private Status status = Status.INACTIVE;


    public Route(List<StopPoint> stops, String transportId, TransportType transportType, int availableSeats){
        this.stops = stops;
        this.transportId = transportId;
        this.transportType = transportType;
        this.availableSeats = availableSeats;
    }

}
