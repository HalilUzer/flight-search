package com.amadeus.flightsearch.Flights.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FlightSearchDto(
        @Future
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime returnTime,

        @NotNull
        @Future
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime departureTime,
        @NotNull
        @NotEmpty
        String arrivalCity,
        @NotNull
        @NotEmpty
        String departureCity

) {
}
