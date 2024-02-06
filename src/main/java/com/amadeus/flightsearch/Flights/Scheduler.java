package com.amadeus.flightsearch.Flights;

import com.amadeus.flightsearch.Airports.Repositories.AirportRepository;
import com.amadeus.flightsearch.Flights.dtos.GetFlightsDto;
import com.amadeus.flightsearch.Flights.entities.Flight;
import com.amadeus.flightsearch.Flights.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableScheduling
@Configuration
@AllArgsConstructor
public class Scheduler {
    private final RestTemplate restTemplate;
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Scheduled(fixedRate = 86400000)
    public void scheduleFixedRateTask() {
        GetFlightsDto getFlightsDto =
                this.restTemplate.getForObject("http://localhost:3000/readjson", GetFlightsDto.class);

        List<Flight> flights = getFlightsDto.getFlights();
        for(Flight flight: flights){
            flight.setFlightId(UUID.randomUUID());
            if(!this.airportRepository.findById(flight.getArrivalAirport().getAirportId()).isPresent()){
                this.airportRepository.save(flight.getArrivalAirport());
            }
            if(!this.airportRepository.findById(flight.getDepartureAirport().getAirportId()).isPresent()){
                this.airportRepository.save(flight.getDepartureAirport());
            }
        }
        flightRepository.saveAll(flights);
    }

}
