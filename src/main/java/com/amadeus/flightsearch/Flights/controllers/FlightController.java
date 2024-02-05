package com.amadeus.flightsearch.Flights.controllers;


import com.amadeus.flightsearch.Flights.dtos.CreateFlightDto;
import com.amadeus.flightsearch.Flights.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
@AllArgsConstructor
public class FlightController {


    private final FlightService flightService;


    @Operation(summary = "Create a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Flight created successfully"),
            @ApiResponse(responseCode = "404", description = "Arrival airport does not exists"),
            @ApiResponse(responseCode = "404", description = "Departure airport does not exits"),
            @ApiResponse(responseCode = "400", description = "Arrival time is before departure time")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFlight(@Valid @RequestBody CreateFlightDto createFlightDto) {
        this.flightService.createFlight(createFlightDto);
    }


}
