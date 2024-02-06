package com.amadeus.flightsearch.Flights.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteFlightDto(
        @NotNull
        UUID flightId
) {
}
