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
}
