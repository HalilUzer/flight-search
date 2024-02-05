package com.amadeus.flightsearch.Airports.controllers;

import com.amadeus.flightsearch.Airports.dtos.CreateAirportDto;
import com.amadeus.flightsearch.Airports.dtos.DeleteAirportDto;
import com.amadeus.flightsearch.Airports.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airport")
@AllArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @Operation(summary = "Create a airport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created"),
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAirport(@Valid @RequestBody CreateAirportDto createAirportDto) {
        this.airportService.createAirport(createAirportDto.city());
    }

    @Operation(summary = "Delelte a airport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Airport deleted"),
            @ApiResponse(responseCode = "404", description = "Airport not found")

    })
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAirport(@Valid @RequestBody DeleteAirportDto deleteAirportDto){
        this.airportService.deleteAirport(deleteAirportDto.id());
    }
}
