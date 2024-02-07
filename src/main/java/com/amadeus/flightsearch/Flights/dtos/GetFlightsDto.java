package com.amadeus.flightsearch.Flights.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class GetFlightsDto implements Serializable {
    private final List<FlightDto> flights;

    public GetFlightsDto(){
        flights = new ArrayList<>();
    }
}
