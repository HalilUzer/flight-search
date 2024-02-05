package com.amadeus.flightsearch.Airports.Repositories;

import com.amadeus.flightsearch.Airports.entities.Airport;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AirportRepository extends CrudRepository<Airport, UUID> {
}
