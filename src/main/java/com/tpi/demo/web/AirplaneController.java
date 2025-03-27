package com.tpi.demo.web;

import com.tpi.demo.models.Airplane.AirplaneDTO;
import com.tpi.demo.service.AirplaneService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService){
        this.airplaneService = airplaneService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<AirplaneDTO> getAll(){
        return airplaneService.getAllAirplane();
    }

}
