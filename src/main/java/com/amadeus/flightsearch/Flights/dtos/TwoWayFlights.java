package com.amadeus.flightsearch.Flights.dtos;

import com.amadeus.flightsearch.Flights.entities.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TwoWayFlights {
    private List<Flight> inboundFlights;
    private List<Flight> outboundFlights;
}
