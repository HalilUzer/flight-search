package com.amadeus.flightsearch.Flights.controllers;


import com.amadeus.flightsearch.Flights.dtos.CreateFlightDto;
import com.amadeus.flightsearch.Flights.dtos.UpdateFlightDto;
import com.amadeus.flightsearch.Flights.entities.Flight;
import com.amadeus.flightsearch.Flights.services.FlightSearchService;
import com.amadeus.flightsearch.Flights.services.FlightService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightController {


    private final FlightService flightService;
    private final FlightSearchService flightSearchService;
    private final String parserPattern;


    @Operation(summary = "Create a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight created successfully"),
            @ApiResponse(responseCode = "404", description = "Arrival airport does not exists",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Departure airport does not exits",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Arrival time is before departure time",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content =
            @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFlight(@Valid @RequestBody CreateFlightDto createFlightDto) {
        this.flightService.createFlight(createFlightDto);
    }


    @Operation(summary = "Query flights")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid datetime format",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })

    @GetMapping("")
    public List<Flight> getFlight(@RequestParam Optional<UUID> id,
                                  @RequestParam Optional<String> arrivalCity,
                                  @RequestParam Optional<String> departureCity,
                                  @RequestParam Optional<String> departureTime) {

        if (id.isPresent()) {
            List<Flight> flights = new ArrayList<>();
            flights.add(this.flightService.findFlightById(id.get()));
            return flights;
        }

        if (departureTime.isPresent() && arrivalCity.isPresent() && departureCity.isPresent()) {
            LocalDateTime localDepartureTime;
            try {
                localDepartureTime = LocalDateTime.parse(departureTime.get(),
                        DateTimeFormatter.ofPattern(parserPattern));
            } catch (DateTimeParseException exp) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid datetime format");
            }
            List<Flight> flights = new ArrayList<>();
            flights.addAll(this.flightSearchService.findFlightsByArrivalAndDepartureCity(arrivalCity.get(),
                    departureCity.get()));
            flights = this.flightSearchService.findFlightsInTime(flights, localDepartureTime);
            return flights;
        }

        if (arrivalCity.isPresent() && departureCity.isPresent()) {
            List<Flight> flights = new ArrayList<>();
            flights.addAll(this.flightSearchService.findFlightsByArrivalAndDepartureCity(arrivalCity.get(),
                    departureCity.get()));
            return flights;
        }


        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
    }


    @Operation(summary = "Update flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "404", description = "Arrival airport not found",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Departure airport not found",
                    content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @PutMapping("")
    void updateFlight(@Valid @RequestBody UpdateFlightDto updateFlightDto) {
        this.flightService.updateFlight(updateFlightDto);
    }

    @Operation(summary = "Delete a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Flight does not exits", content =
            @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable UUID id) {
        this.flightService.deleteFlight(id);
    }
}
