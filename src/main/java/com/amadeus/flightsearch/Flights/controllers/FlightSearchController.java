package com.amadeus.flightsearch.Flights.controllers;

import com.amadeus.flightsearch.Flights.entities.Flight;
import com.amadeus.flightsearch.Flights.services.FlightSearchService;
import com.amadeus.flightsearch.Flights.services.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights/search")
@AllArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;
    private final TimeService timeService;



    @Operation(summary = "Search one-way or round-trip flights with lowest price")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Success")
    )
    @GetMapping
    List<Flight> searchFlights(@RequestParam String arrivalCity,
                               @RequestParam String departureCity,
                               @RequestParam String departureTime,
                               @RequestParam Optional<String> returnTime) {
        if (returnTime.isPresent()) {
            LocalDateTime returnLocalDateTime = timeService.parseFrom(returnTime.get());
            LocalDateTime departureLocalDateTime = timeService.parseFrom(departureTime);

            return this.flightSearchService.searchRoundTripFlights(arrivalCity,
                    departureCity,
                    returnLocalDateTime,
                    departureLocalDateTime);
        } else {
            LocalDateTime departureLocalDateTime = timeService.parseFrom(departureTime);
            return this.flightSearchService.searchOneWayFlight(arrivalCity, departureCity, departureLocalDateTime);
        }
    }
}
