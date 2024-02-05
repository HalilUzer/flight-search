package com.amadeus.flightsearch.Airports.services;

import com.amadeus.flightsearch.Airports.Repositories.AirportRepository;
import com.amadeus.flightsearch.Airports.entities.Airport;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Transactional
public class AirportService {

    private final AirportRepository airportRepository;

    public void createAirport(String city) throws ResponseStatusException{
        Airport airport = new Airport(city);
        if(!this.airportRepository.findAirportByCity(city).isEmpty()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airport already exists");
        }
        this.airportRepository.save(airport);
    }

    public void deleteAirport(String city) throws  ResponseStatusException{
        Airport airport = this.airportRepository.findAirportByCity(city).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found"));
        this.airportRepository.deleteByCity(airport.getCity());
    }

}
