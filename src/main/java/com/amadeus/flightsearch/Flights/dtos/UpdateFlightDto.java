package com.amadeus.flightsearch.Flights.dtos;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UpdateFlightDto(
        @NotNull
        UUID flightId,
        Optional<UUID> departureAirportId,
        Optional<UUID> arrivalAirportId,
        Optional<LocalDateTime> departureTime,
        Optional<LocalDateTime> arrivalTime,
        Optional<Integer> price
){}

