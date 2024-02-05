package com.amadeus.flightsearch.Flights.services;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Airports.services.AirportService;
import com.amadeus.flightsearch.Flights.Repositories.FlightRepository;
import com.amadeus.flightsearch.Flights.dtos.FlightSearchDto;
import com.amadeus.flightsearch.Flights.dtos.TwoWayFlights;
import com.amadeus.flightsearch.Flights.entities.Flight;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightSearchService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;

    public TwoWayFlights searchTwoWayFlights(FlightSearchDto flightSearchDto) {
        List<Airport> arrivalAirports = airportService.findAllAirportsByCity(flightSearchDto.arrivalCity());
        List<Airport> departureAirports = airportService.findAllAirportsByCity(flightSearchDto.departureCity());

        List<Flight> outboundFlights = new ArrayList<>();
        List<Flight> inboundFlights = new ArrayList<>();

        outboundFlights.addAll(findFlightsByArrivalAirportsAndDepartureAirports(arrivalAirports, departureAirports));
        inboundFlights.addAll(findFlightsByArrivalAirportsAndDepartureAirports(departureAirports, arrivalAirports));


        List<Flight> outboundFlightsInTime = this.findFlightsInTime(
                outboundFlights, flightSearchDto.departureTime());
        List<Flight> inboundFlightsInTime = this.findFlightsInTime(
                inboundFlights, flightSearchDto.returnTime()
        );

        TwoWayFlights twoWayFlights = new TwoWayFlights(outboundFlightsInTime, inboundFlightsInTime);

        return twoWayFlights;
    }


    private List<Flight> findFlightsByArrivalAirportAndAndDepartureAirport(Airport arrivalAirport, Airport departureAirport) {
        List<Flight> emptyList = new ArrayList<>();
        return this.flightRepository
                .findFlightsByArrivalAirportAndAndDepartureAirport(arrivalAirport, departureAirport).orElse(emptyList);
    }


    private List<Flight> findFlightsByArrivalAirportsAndDepartureAirports(List<Airport> arrivalAirports, List<Airport> departureAirports) {
        List<Flight> flights = new ArrayList<>();
        for (Airport arrivalAirport : arrivalAirports) {
            for (Airport departureAirport : departureAirports) {
                flights.addAll(this.findFlightsByArrivalAirportAndAndDepartureAirport(arrivalAirport, departureAirport));
            }
        }
        return flights;
    }

    private List<Flight> findFlightsInTime(List<Flight> flights, LocalDateTime time) {
        List<Flight> flightsInTime = new ArrayList<>();

        for (Flight flight : flights) {
            if (flight.getDepartureTime().isBefore(time)) {
                flightsInTime.add(flight);
            }
        }
        return flightsInTime;
    }


}
