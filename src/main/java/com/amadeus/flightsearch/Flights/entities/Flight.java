package com.amadeus.flightsearch.Flights.entities;

import com.amadeus.flightsearch.Airports.entities.Airport;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity(name = "flights")
@Getter @Setter  @AllArgsConstructor @NoArgsConstructor
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
