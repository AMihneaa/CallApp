package com.tpi.demo.models.Point;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StopPoint {

    private String location;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public StopPoint(){}

    public StopPoint(String location, LocalDateTime arrivalTime, LocalDateTime departureTime){
        this.location = location;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
