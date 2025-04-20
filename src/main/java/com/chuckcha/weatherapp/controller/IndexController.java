package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.api.OpenWeatherClient;
import com.chuckcha.weatherapp.dto.location.LocationFromDatabaseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherWeatherResponseDto;
import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final LocationService locationService;
    private final OpenWeatherClient openWeatherClient;

    public IndexController(LocationService locationService, OpenWeatherClient openWeatherClient) {
        this.locationService = locationService;
        this.openWeatherClient = openWeatherClient;
    }

    @GetMapping
    public String getIndex(@ModelAttribute("user") UserLoginDto user, Model model) {
        List<LocationFromDatabaseDto> locations = locationService.getLocationsByUserId(user.id());
        if (!locations.isEmpty()) {
            List<OpenWeatherWeatherResponseDto> locationsWithWeather = openWeatherClient.getWeather(locations);
            model.addAttribute("locations", locationsWithWeather);
        }
        return "index";
    }
}
