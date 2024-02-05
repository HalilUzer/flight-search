package com.amadeus.flightsearch.Airports.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteAirportDto(
        @NotNull
        UUID id
) {
}
