package com.amadeus.flightsearch.Airports.repositories;

import com.amadeus.flightsearch.Airports.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {
    @Override
    Optional<Airport> findById(UUID id);


    void deleteByAirportId(UUID airportId);
    Optional<List<Airport>> findAllByCity(String city);
}
