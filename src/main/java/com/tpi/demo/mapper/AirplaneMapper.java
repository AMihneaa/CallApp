package com.tpi.demo.mapper;

import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneDTO;
import org.springframework.stereotype.Component;

@Component
public class AirplaneMapper {

    public AirplaneDTO modelToDTO(Airplane airplane){
        return new AirplaneDTO(airplane.getModel(), airplane.getSeatingCapacity(), airplane.getAirline(), airplane.getStatus());
    }

}
