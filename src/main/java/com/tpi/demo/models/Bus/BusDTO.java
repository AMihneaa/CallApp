package com.tpi.demo.models.Bus;

import com.tpi.demo.models.Enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDTO {

    private String model;

    private int seatingCapacity;

    private String company;

    private Status status = Status.INACTIVE;

}
