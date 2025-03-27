package com.tpi.demo.service;

import com.tpi.demo.mapper.AirplaneMapper;
import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneDTO;
import com.tpi.demo.models.Airplane.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;

    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneMapper airplaneMapper){
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
    }

    public List<AirplaneDTO> getAllAirplane(){
        List<Airplane> airplanes = airplaneRepository.findAll();

        return airplanes.stream().map(
                airplane -> airplaneMapper.modelToDTO(airplane)
        ).collect(Collectors.toList());
    }
}
