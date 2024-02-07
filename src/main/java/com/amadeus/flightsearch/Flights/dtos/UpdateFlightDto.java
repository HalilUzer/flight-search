package com.amadeus.flightsearch.Flights.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record UpdateFlightDto(
        @NotNull
        UUID flightId,
        Optional<UUID> departureAirportId,
        Optional<UUID> arrivalAirportId,
        @Future
        Optional<LocalDateTime> departureTime,
        @Future
        Optional<LocalDateTime> arrivalTime,
        Optional<Integer> price
){}

