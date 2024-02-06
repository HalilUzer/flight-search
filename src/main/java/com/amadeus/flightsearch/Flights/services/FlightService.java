package com.amadeus.flightsearch.Flights.services;

import com.amadeus.flightsearch.Airports.Repositories.AirportRepository;
import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Flights.Repositories.FlightRepository;
import com.amadeus.flightsearch.Flights.dtos.CreateFlightDto;
import com.amadeus.flightsearch.Flights.dtos.DeleteFlightDto;
import com.amadeus.flightsearch.Flights.entities.Flight;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final String parserPattern;

    public void createFlight(CreateFlightDto createFlightDto) {
        Airport arrivalAirport = airportRepository
                .findById(createFlightDto.arrivalAirportId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrival Airport Not Found"));
        Airport departureAirport = airportRepository
                .findById(createFlightDto.departureAirportId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departure Airport Not Found"));

      if(!createFlightDto.departureTime().isBefore(createFlightDto.arrivalTime())){
          throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure time is before arrival time");
      }

        Flight flight = new Flight(UUID.randomUUID(), arrivalAirport, departureAirport,
                createFlightDto.departureTime(), createFlightDto.arrivalTime(), createFlightDto.price());
        flightRepository.save(flight);
    }

    public void deleteFlight(DeleteFlightDto deleteFlightDto){
        Flight flight = this.flightRepository
                .findById(deleteFlightDto.flightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        this.flightRepository.deleteById(flight.getFlightId());
    }

    public Flight findFlightById(UUID id){
        return this.flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }




}
