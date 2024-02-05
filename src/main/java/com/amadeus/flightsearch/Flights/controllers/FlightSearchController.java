package com.amadeus.flightsearch.Flights.controllers;

import com.amadeus.flightsearch.Flights.dtos.FlightSearchDto;
import com.amadeus.flightsearch.Flights.dtos.TwoWayFlights;
import com.amadeus.flightsearch.Flights.services.FlightSearchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight/search")
@AllArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @GetMapping
    TwoWayFlights searchFlights(@Valid @RequestBody FlightSearchDto flightSearchDto){
        return this.flightSearchService.searchTwoWayFlights(flightSearchDto);
    }
}
