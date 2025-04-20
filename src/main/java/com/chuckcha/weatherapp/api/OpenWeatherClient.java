package com.chuckcha.weatherapp.api;

import com.chuckcha.weatherapp.dto.location.LocationFromDatabaseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherLocationsResponseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherWeatherResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public OpenWeatherClient(RestTemplate restTemplate, @Value("${openweather.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<OpenWeatherLocationsResponseDto> getLocationsByCityName(String cityName) {
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=%s".formatted(cityName, apiKey);
        return Arrays.stream(Objects.requireNonNull(
                restTemplate.getForObject(url, OpenWeatherLocationsResponseDto[].class))).toList();
    }

    public List<OpenWeatherWeatherResponseDto> getWeather(List<LocationFromDatabaseDto> locations) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";
        List<OpenWeatherWeatherResponseDto> locationsWithWeather = new ArrayList<>();
        for (LocationFromDatabaseDto location : locations) {
            String currentUrl = url.formatted(location.lat(), location.lon(), apiKey);
            OpenWeatherWeatherResponseDto locationResponse = restTemplate.getForObject(currentUrl, OpenWeatherWeatherResponseDto.class);
            locationResponse.setId(location.id());
            locationResponse.setName(location.name());
            locationsWithWeather.add(locationResponse);
        }
        return locationsWithWeather;
    }

}
