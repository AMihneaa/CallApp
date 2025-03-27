package com.tpi.demo.models.Airplane;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("airplanes")
@Getter
@Setter
public class Airplane {

    @Id
    @Field("tail number")
    private String id;

    private String model;

    private int seatingCapacity;

    private String airline;

    private String status;

    public Airplane(String model, int seatingCapacity, String airline, String status){
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.airline = airline;
        this.status = status;
    }

}
