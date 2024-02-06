package com.amadeus.flightsearch.Flights.controllers;

import com.amadeus.flightsearch.Flights.entities.Flight;
import com.amadeus.flightsearch.Flights.services.FlightSearchService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights/search")
@AllArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;
    private final String parserPattern;

    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Success")
    )
    @GetMapping
    List<Flight> searchFlights(@RequestParam String arrivalCity,
                               @RequestParam String departureCity,
                               @RequestParam String departureTime,
                               @RequestParam Optional<String> returnTime) {
        if (returnTime.isPresent()) {
            LocalDateTime returnLocalDateTime;
            LocalDateTime departureLocalDateTime;
            try {
                returnLocalDateTime = LocalDateTime.parse(returnTime.get(),
                        DateTimeFormatter.ofPattern(parserPattern));
                departureLocalDateTime = LocalDateTime.parse(departureTime,
                        DateTimeFormatter.ofPattern(parserPattern));

            } catch (DateTimeParseException exp) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid datetime format");
            }

            return this.flightSearchService.searchTwoWayFlights(arrivalCity,
                    departureCity,
                    returnLocalDateTime,
                    departureLocalDateTime);
        } else {
            LocalDateTime departureLocalDateTime = LocalDateTime.parse(departureTime,
                    DateTimeFormatter.ofPattern(parserPattern));
            return this.flightSearchService.searchOneWayFlight(arrivalCity, departureCity, departureLocalDateTime);
        }
    }
}
