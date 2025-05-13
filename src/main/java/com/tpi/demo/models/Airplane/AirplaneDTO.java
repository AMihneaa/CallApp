package com.tpi.demo.models.Airplane;

import com.tpi.demo.models.Enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirplaneDTO {

    private String model;

    private int seatingCapacity;

    private String airline;

    private Status status;

    public AirplaneDTO(String model, int seatingCapacity, String airline, Status status){
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.airline = airline;
        this.status = status;
    }
}
