package com.amadeus.flightsearch.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class FlighSearchConfig {
    @Bean
    public String getParserPattern(){
        return "yyyy-MM-dd HH:mm";
    }

}
