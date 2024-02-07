package com.amadeus.flightsearch.Flights.services;

import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class TimeService {

    public LocalDateTime parseFrom(String time){

        try{
            return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));
        }
        catch (Exception exp){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid datetime format");
        }
    }
}
