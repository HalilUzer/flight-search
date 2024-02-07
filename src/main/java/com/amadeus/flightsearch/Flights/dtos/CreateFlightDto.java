package com.amadeus.flightsearch.Flights.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateFlightDto(



        @NotNull
        UUID departureAirportId,
        @NotNull
        UUID arrivalAirportId,

        @NotNull
        @Future
        LocalDateTime departureTime,

        @NotNull
        @Future
        LocalDateTime arrivalTime,

        @NotNull
        int price
) {
}
