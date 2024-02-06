package com.amadeus.flightsearch.Airports.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAirportDto(
        @NotNull
        UUID airportId,
        @NotNull
        @NotEmpty
        String newCity
) {
}
