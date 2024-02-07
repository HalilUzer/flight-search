package com.amadeus.flightsearch.Airports.controllers;

import com.amadeus.flightsearch.Airports.dtos.CreateAirportDto;
import com.amadeus.flightsearch.Airports.dtos.UpdateAirportDto;
import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Airports.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/airports")
@AllArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @Operation(summary = "Create a airport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAirport(@Valid @RequestBody CreateAirportDto createAirportDto) {
        this.airportService.createAirport(createAirportDto.city());
    }

    @Operation(summary = "Delete a airport by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport deleted"),
            @ApiResponse(responseCode = "404", description = "Airport not found",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = Void.class)))

    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAirport(@PathVariable UUID id) {
        this.airportService.deleteAirport(id);
    }

    @Operation(summary = "Update airport name by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport updated"),
            @ApiResponse(responseCode = "404", description = "Airport not found",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void updateAirport(@Valid @RequestBody UpdateAirportDto updateAirportDto) {
        this.airportService.updateAirport(updateAirportDto);
    }

    @Operation(summary = "Query airports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> getAirportsByCity(@RequestParam Optional<String> city, @RequestParam Optional<UUID> id){
        if(city.isPresent() && id.isPresent()){
            List<Airport> airports = new ArrayList<>();
            airports.add(this.airportService.findAirportById(id.get()));
            return airports;
        }

       if(city.isPresent()){
           return this.airportService.findAllAirportsByCity(city.get());
       }

       if(id.isPresent()){
           List<Airport> airports = new ArrayList<>();
           airports.add(this.airportService.findAirportById(id.get()));
           return airports;
       }

       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
    }

}
