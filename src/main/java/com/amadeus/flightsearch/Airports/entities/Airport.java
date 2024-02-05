package com.amadeus.flightsearch.Airports.entities;

import com.amadeus.flightsearch.Flights.entities.Flight;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity(name = "airports")
@Getter
@Setter
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID airportId;

    @Column
    private String city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departureAirport")
    private List<Flight> departureFlights;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrivalAirport")
    private List<Flight> arrivalFlights;
    public Airport(String city) {
        this.city = city;
    }
}
