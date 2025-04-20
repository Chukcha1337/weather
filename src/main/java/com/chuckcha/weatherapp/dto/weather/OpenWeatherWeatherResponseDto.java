package com.chuckcha.weatherapp.dto.weather;

import com.chuckcha.weatherapp.util.WeatherDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize(using = WeatherDeserializer.class)
@Getter
public class OpenWeatherWeatherResponseDto {

    @Setter
    private int id;
    @Setter
    private String name;

    private final String icon;
    private final String temp;
    private final String country;
    private final String feelsLike;
    private final String description;
    private final int humidity;

    public OpenWeatherWeatherResponseDto(String icon, String temp, String country, String feelsLike, String description, int humidity) {
        this.icon = icon;
        this.temp = temp;
        this.country = country;
        this.feelsLike = feelsLike;
        this.description = description;
        this.humidity = humidity;
    }
}
