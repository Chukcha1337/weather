package com.chuckcha.weatherapp.dto.weather;

import com.chuckcha.weatherapp.util.CountryCodeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;

public record OpenWeatherLocationsResponseDto(
        String name,
        @JsonDeserialize(using = CountryCodeDeserializer.class)
        String country,
        String state,
        BigDecimal lat,
        BigDecimal lon) {
}
