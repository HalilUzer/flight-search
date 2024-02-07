package com.amadeus.flightsearch.Flights.dtos;

import com.amadeus.flightsearch.Airports.entities.Airport;

public record FlightDto(
        String arrivalTime,
        String departureTime,
        Airport arrivalAirport,
        Airport departureAirport,
        int price
) {
}
