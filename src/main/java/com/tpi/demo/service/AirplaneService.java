package com.tpi.demo.service;

import com.tpi.demo.mapper.TransportMapper;
import com.tpi.demo.models.Airplane.Airplane;
import com.tpi.demo.models.Airplane.AirplaneDTO;
import com.tpi.demo.models.Airplane.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final TransportMapper transportMapper;

    public AirplaneService(AirplaneRepository airplaneRepository, TransportMapper transportMapper){
        this.airplaneRepository = airplaneRepository;
        this.transportMapper = transportMapper;
    }

    public List<AirplaneDTO> getAllAirplane(){
        List<Airplane> airplanes = airplaneRepository.findAll();

        return airplanes.stream().map(
                airplane -> transportMapper.modelToDTO(airplane)
        ).collect(Collectors.toList());
    }
}
