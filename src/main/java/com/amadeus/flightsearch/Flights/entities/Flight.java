package com.amadeus.flightsearch.Flights.entities;

import com.amadeus.flightsearch.Airports.entities.Airport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "flights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID flightId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivalAirportId")
    private Airport arrivalAirportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartureAirportId")
    private Airport departureAirportId;
}
