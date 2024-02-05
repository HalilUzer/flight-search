package com.amadeus.flightsearch.Flights.Repositories;

import com.amadeus.flightsearch.Flights.entities.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FlightRepository extends CrudRepository<Flight, UUID> {
}
