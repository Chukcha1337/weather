package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.api.OpenWeatherClient;
import com.chuckcha.weatherapp.dto.location.LocationToDatabaseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherLocationsResponseDto;
import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocationsController {

    private final OpenWeatherClient openWeatherClient;
    private final LocationService locationService;

    public LocationsController(OpenWeatherClient openWeatherClient, LocationService locationService) {
        this.openWeatherClient = openWeatherClient;
        this.locationService = locationService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("cityName") String cityName, Model model) {
        List<OpenWeatherLocationsResponseDto> locationsByCityName = openWeatherClient.getLocationsByCityName(cityName);
        model.addAttribute("cityName", cityName);
        model.addAttribute("searchResults", locationsByCityName);
        return "search-results";
    }

    @PostMapping("/locations")
    public String saveLocation(@ModelAttribute("location") LocationToDatabaseDto locationDto,
                               @ModelAttribute("user") UserLoginDto user) {
        locationService.saveLocation(locationDto, user.id());
        return "redirect:/index";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(@RequestParam("locationId") int locationId) {
        locationService.delete(locationId);
        return "redirect:/index";
    }
}
