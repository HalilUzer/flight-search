package com.amadeus.flightsearch.Flights;

import com.amadeus.flightsearch.Airports.repositories.AirportRepository;
import com.amadeus.flightsearch.Flights.dtos.FlightDto;
import com.amadeus.flightsearch.Flights.dtos.GetFlightsDto;
import com.amadeus.flightsearch.Flights.entities.Flight;
import com.amadeus.flightsearch.Flights.repositories.FlightRepository;
import com.amadeus.flightsearch.Flights.services.TimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableScheduling
@Configuration
@AllArgsConstructor
public class Scheduler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final TimeService timeService;

    @Scheduled(fixedRate = 86400000)


    public void scheduleFixedRateTask() {

         /*GetFlightsDto getFlightsDto =
                this.restTemplate.getForObject("http://localhost:3000/readjson", GetFlightsDto.class);*/

        String data = "{ \"flights\": [\n" +
                "    {\n" +
                "        \"arrivalAirport\": {\n" +
                "            \"airportId\": \"fc464fb1-9861-4f8c-9f78-0d7ba2db13b8\",\n" +
                "            \"city\": \"Tokyo\"\n" +
                "        },\n" +
                "        \"departureAirport\": {\n" +
                "            \"airportId\": \"ff09e732-7fc4-4545-ac52-26bf04f80f12\",\n" +
                "            \"city\": \"Paris\"\n" +
                "        },\n" +
                "        \n" +
                "        \"departureTime\": \"2024-12-13T13:15:00.000000Z\",\n" +
                "        \"arrivalTime\": \"2024-12-14T13:15:00.000000Z\",\n" +
                "        \"price\": 75\n" +
                "    },\n" +
                "    {\n" +
                "        \"arrivalAirport\": {\n" +
                "            \"airportId\": \"577cb8f8-2575-4353-a451-ca963dd9db26\",\n" +
                "            \"city\": \"Milan\"\n" +
                "        },\n" +
                "        \"departureAirport\": {\n" +
                "            \"airportId\": \"8ca0cf18-199b-4592-8038-bb2d7f1dcd89\",\n" +
                "            \"city\": \"Dubai\"\n" +
                "        },\n" +
                "        \n" +
                "        \"departureTime\": \"2024-12-13T13:15:00.000000Z\",\n" +
                "        \"arrivalTime\": \"2024-12-14T13:15:00.000000Z\",\n" +
                "        \"price\": 75\n" +
                "    },\n" +
                "    {\n" +
                "        \"arrivalAirport\": {\n" +
                "            \"airportId\": \"c347bad1-1163-42c1-a860-e3d56225910f\",\n" +
                "            \"city\": \"Barcelona\"\n" +
                "        },\n" +
                "        \"departureAirport\": {\n" +
                "            \"airportId\": \"732d9dd2-74e4-4b99-b63e-24e7d8ff3bd3\",\n" +
                "            \"city\": \"Prague\"\n" +
                "        },\n" +
                "        \n" +
                "        \"departureTime\": \"2024-12-13T13:15:00.000000Z\",\n" +
                "        \"arrivalTime\": \"2024-12-13T13:15:00.000000Z\",\n" +
                "        \"price\": 75\n" +
                "    }\n" +
                "]" +
                "}";


        GetFlightsDto getFlightsDto;

        try {
            getFlightsDto = objectMapper.readValue(data, GetFlightsDto.class);
        } catch (Exception exp) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        List<Flight> flights = new ArrayList<>();

        for(FlightDto flightDto: getFlightsDto.getFlights()){
            Flight flight = new Flight(
                    UUID.randomUUID(),
                    flightDto.arrivalAirport(),
                    flightDto.departureAirport(),
                    timeService.parseFrom(flightDto.departureTime()),
                    timeService.parseFrom(flightDto.arrivalTime()),
                    flightDto.price()
            );
            flights.add(flight);
        }

        for (Flight flight : flights) {
            if (!this.airportRepository.findById(flight.getArrivalAirport().getAirportId()).isPresent()) {
                this.airportRepository.save(flight.getArrivalAirport());
            }
            if (!this.airportRepository.findById(flight.getDepartureAirport().getAirportId()).isPresent()) {
                this.airportRepository.save(flight.getDepartureAirport());
            }
        }
        flightRepository.saveAll(flights);
    }

}


