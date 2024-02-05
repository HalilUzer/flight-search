package com.amadeus.flightsearch.Airports.Repositories;

import com.amadeus.flightsearch.Airports.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {
    @Override
    Optional<Airport> findById(UUID uuid);


    void deleteByCity(String city);
    Optional<Airport> findAirportByCity(String city);
}
