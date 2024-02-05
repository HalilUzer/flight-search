package com.amadeus.flightsearch.Flights.entities;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "flights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID flightId;

    @JsonIgnoreProperties({"departureFlights", "arrivalFlights", "airportID"})
    @ManyToOne
    @JoinColumn(name = "arrivalAirportId")
    private Airport arrivalAirport;

    @JsonIgnoreProperties({"departureFlights", "arrivalFlights", "airportID"})
    @ManyToOne
    @JoinColumn(name = "DepartureAirportId")
    private Airport departureAirport;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime departureTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime arrivalTime;

    @Column
    private int price;
}
