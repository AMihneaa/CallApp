package com.tpi.demo.models.Route;

import com.tpi.demo.models.Enums.TransportType;
import com.tpi.demo.models.Point.StopPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoundRoute {
    private String routeID;

    private List<StopPoint> stopPoints;

    private TransportType transportType;
}