package com.amadeus.flightsearch.Flights.repositories;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Flights.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<List<Flight>> findAllFlightsByDepartureTimeAfter(LocalDateTime departureTime);
    Optional<List<Flight>> findFlightsByArrivalAirportAndAndDepartureAirport(Airport arrivalAirport, Airport departureAirport);
}