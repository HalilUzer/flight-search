package com.amadeus.flightsearch.Airports.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateAirportDto(
        @NotNull
        @NotEmpty
        String city
) {
}