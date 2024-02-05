package com.amadeus.flightsearch.Flights.Repositories;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Flights.entities.Flight;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<List<Flight>> findAllFlightsByDepartureTimeAfter(LocalDateTime departureTime);
    Optional<List<Flight>> findFlightsByArrivalAirportAndAndDepartureAirport(Airport arrivalAirport, Airport departureAirport);
}
