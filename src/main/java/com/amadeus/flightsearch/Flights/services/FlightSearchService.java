package com.amadeus.flightsearch.Flights.services;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.amadeus.flightsearch.Airports.services.AirportService;
import com.amadeus.flightsearch.Flights.repositories.FlightRepository;
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

    public List<Flight> searchTwoWayFlights(String arrivalCity, String departureCity, LocalDateTime returnTime, LocalDateTime departureTime) {
        List<Airport> arrivalAirports = airportService.findAllAirportsByCity(arrivalCity);
        List<Airport> departureAirports = airportService.findAllAirportsByCity(departureCity);

        List<Flight> outboundFlights = new ArrayList<>();
        List<Flight> inboundFlights = new ArrayList<>();

        outboundFlights.addAll(findFlightsByArrivalAndDepartureAirports(arrivalAirports, departureAirports));
        inboundFlights.addAll(findFlightsByArrivalAndDepartureAirports(departureAirports, arrivalAirports));


        List<Flight> outboundFlightsInTime = this.findFlightsInTime(
                outboundFlights, departureTime);
        List<Flight> inboundFlightsInTime = this.findFlightsInTime(
                inboundFlights, returnTime
        );

        if(outboundFlightsInTime.isEmpty() || inboundFlightsInTime.isEmpty()){
            return new ArrayList<>();
        }

        Flight outboundFlightInTimeWithLowestPrice = this.findFlightWithLowestPrice(outboundFlightsInTime);
        Flight inboundFlightInTimeWithLowestPrice = this.findFlightWithLowestPrice(inboundFlightsInTime);
        List<Flight> flights = new ArrayList<>();
        flights.add(outboundFlightInTimeWithLowestPrice);
        flights.add(inboundFlightInTimeWithLowestPrice);

        return flights;
    }


    private Flight findFlightWithLowestPrice(List<Flight> flights){
        int price = flights.get(0).getPrice();
        Flight flightWithLowestPrice = flights.get(0);
        for(Flight flight : flights){
            if(price > flight.getPrice()){
                price = flight.getPrice();
                flightWithLowestPrice = flight;
            }
        }
        return flightWithLowestPrice;
    }

    public List<Flight> searchOneWayFlight(String arrivalCity, String departureCity, LocalDateTime departureTime){
        List<Flight> oneWayFlights = new ArrayList<>();
        oneWayFlights.addAll(this.findFlightsByArrivalAndDepartureCity(arrivalCity, departureCity));
        oneWayFlights = this.findFlightsInTime(oneWayFlights, departureTime);
        if(oneWayFlights.isEmpty()){
            return new ArrayList<>();
        }
        List<Flight> oneWayFlightWithLowestPrice = new ArrayList<>();
        oneWayFlightWithLowestPrice.add(this.findFlightWithLowestPrice(oneWayFlights));
        return oneWayFlightWithLowestPrice;
    }


    private List<Flight> findFlightsByArrivalAndAndDepartureAirport(Airport arrivalAirport, Airport departureAirport) {
        List<Flight> emptyList = new ArrayList<>();
        return this.flightRepository
                .findFlightsByArrivalAirportAndAndDepartureAirport(arrivalAirport, departureAirport).orElse(emptyList);
    }


    public List<Flight> findFlightsByArrivalAndDepartureAirports(List<Airport> arrivalAirports,
                                                                 List<Airport> departureAirports) {
        List<Flight> flights = new ArrayList<>();
        for (Airport arrivalAirport : arrivalAirports) {
            for (Airport departureAirport : departureAirports) {
                flights.addAll(this.findFlightsByArrivalAndAndDepartureAirport(arrivalAirport, departureAirport));
            }
        }
        return flights;
    }

    public List<Flight> findFlightsByArrivalAndDepartureCity(String arrivalCity, String departureCity) {
        List<Airport> arrivalAirports = this.airportService.findAllAirportsByCity(arrivalCity);
        List<Airport> departureAirports = this.airportService.findAllAirportsByCity(departureCity);
        return this.findFlightsByArrivalAndDepartureAirports(arrivalAirports, departureAirports);
    }

    public List<Flight> findFlightsInTime(List<Flight> flights, LocalDateTime time) {
        List<Flight> flightsInTime = new ArrayList<>();

        for (Flight flight : flights) {
            if (flight.getDepartureTime().isBefore(time) && flight.getDepartureTime().plusDays(1).isAfter(time)) {
                flightsInTime.add(flight);
            }
        }
        return flightsInTime;
    }

}
