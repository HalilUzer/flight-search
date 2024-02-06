package com.amadeus.flightsearch.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class FlighSearchConfigs {
    @Bean
    public String getParserPattern(){
        return "yyyy-MM-dd HH:mm";
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
