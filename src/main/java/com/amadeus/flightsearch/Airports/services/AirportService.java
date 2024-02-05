package com.amadeus.flightsearch.Airports.services;

import com.amadeus.flightsearch.Airports.Repositories.AirportRepository;
import com.amadeus.flightsearch.Airports.entities.Airport;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AirportService {

    private final AirportRepository airportRepository;

    public void createAirport(String city) throws ResponseStatusException{
        Airport airport = new Airport(city);
        this.airportRepository.save(airport);
    }

    public void deleteAirport(UUID id) throws  ResponseStatusException{
        Airport airport = this.airportRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found"));
        this.airportRepository.deleteByAirportId(airport.getAirportId());
    }

    public List<Airport> findAllAirportsByCity(String city){
        return this.airportRepository.findAllByCity(city).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found"));
    }

}
