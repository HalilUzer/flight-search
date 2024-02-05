package com.amadeus.flightsearch.Flights.Repositories;

import com.amadeus.flightsearch.Flights.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
}
