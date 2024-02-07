package com.amadeus.flightsearch.Flights.services;

import com.amadeus.flightsearch.Airports.Repositories.AirportRepository;
import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Flights.dtos.UpdateFlightDto;
import com.amadeus.flightsearch.Flights.repositories.FlightRepository;
import com.amadeus.flightsearch.Flights.dtos.CreateFlightDto;
import com.amadeus.flightsearch.Flights.entities.Flight;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
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


    public Flight findFlightById(UUID id){
        return this.flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }


    public void deleteFlight(UUID id){
        this.flightRepository.deleteById(id);
    }


    public void updateFlight(UpdateFlightDto updateFlightDto){
        Flight flight = this.flightRepository.findById(updateFlightDto.flightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        if(updateFlightDto.arrivalTime().isPresent()){
            flight.setArrivalTime(updateFlightDto.arrivalTime().get());
        }

        if(updateFlightDto.departureTime().isPresent()){
            flight.setDepartureTime(updateFlightDto.departureTime().get());
        }

        if(updateFlightDto.arrivalAirportId().isPresent()){
            Airport arrivalAirport = this.airportRepository.findById(updateFlightDto.arrivalAirportId().get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrival airport not found"));
            flight.setArrivalAirport(arrivalAirport);
        }

        if (updateFlightDto.departureAirportId().isPresent()){
            Airport departureAirport = this.airportRepository.findById(updateFlightDto.departureAirportId().get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departure airport not found"));
            flight.setDepartureAirport(departureAirport);
        }

        if(updateFlightDto.price().isPresent()){
            flight.setPrice(updateFlightDto.price().get());
        }
    }

}
