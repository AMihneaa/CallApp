package com.tpi.demo.models.Bus;

import com.tpi.demo.models.Enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("buses")
@Getter
@Setter
public class Bus {

    @Id
    private String id;

    private String model;

    private int seatingCapacity;

    private String company;

    private Status status = Status.INACTIVE;

    public Bus(String model, int seatingCapacity, String company){
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.company = company;
    }
}
