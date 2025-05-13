package com.tpi.demo.models.Airplane;

import com.tpi.demo.models.Enums.Status;
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

    private Status status = Status.INACTIVE;

    public Airplane(String model, int seatingCapacity, String airline){
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.airline = airline;
    }

}
