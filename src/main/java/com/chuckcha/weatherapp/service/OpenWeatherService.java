package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.location.LocationResponseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherLocationsResponseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OpenWeatherService {

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String apiKey;
    @Value("${openweather.geo.url}")
    private String geoUrl;
    @Value("${openweather.data.url}")
    private String dataUrl;

    public List<OpenWeatherLocationsResponseDto> getLocationsByCityName(String cityName) {
        String url = geoUrl.formatted(cityName, apiKey);
        OpenWeatherLocationsResponseDto[] response = restTemplate.getForObject(url, OpenWeatherLocationsResponseDto[].class);
        if (response == null || response.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(response);

    }

    public List<OpenWeatherWeatherResponseDto> getWeather(List<LocationResponseDto> locations) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";
        List<OpenWeatherWeatherResponseDto> locationsWithWeather = new ArrayList<>();
        for (LocationResponseDto location : locations) {
            String currentUrl = url.formatted(location.lat(), location.lon(), apiKey);
            OpenWeatherWeatherResponseDto locationResponse = restTemplate.getForObject(currentUrl, OpenWeatherWeatherResponseDto.class);
            locationResponse.setId(location.id());
            locationResponse.setName(location.name());
            locationsWithWeather.add(locationResponse);
        }
        return locationsWithWeather;
    }

}
