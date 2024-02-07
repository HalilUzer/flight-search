package com.amadeus.flightsearch.Flights.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FlightSearchDto(
        @Future
        LocalDateTime returnTime,

        @NotNull
        @Future
        LocalDateTime departureTime,
        @NotNull
        @NotEmpty
        String arrivalCity,
        @NotNull
        @NotEmpty
        String departureCity

) {
}
