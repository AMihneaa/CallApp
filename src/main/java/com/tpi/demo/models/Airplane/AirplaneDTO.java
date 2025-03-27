package com.tpi.demo.models.Airplane;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirplaneDTO {

    private String model;

    private int seatingCapacity;

    private String airline;

    private String status;

    public AirplaneDTO(String model, int seatingCapacity, String airline, String status){
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.airline = airline;
        this.status = status;
    }
}
