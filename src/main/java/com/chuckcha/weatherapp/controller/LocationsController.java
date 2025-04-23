package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.dto.location.CitySearchDto;
import com.chuckcha.weatherapp.dto.location.LocationResponseDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherWeatherResponseDto;
import com.chuckcha.weatherapp.service.OpenWeatherService;
import com.chuckcha.weatherapp.dto.location.LocationRequestDto;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherLocationsResponseDto;
import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocationsController {

    private final OpenWeatherService openWeatherService;
    private final LocationService locationService;

    public LocationsController(OpenWeatherService openWeatherService, LocationService locationService) {
        this.openWeatherService = openWeatherService;
        this.locationService = locationService;
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("citySearchDto") @Valid CitySearchDto citySearchDto,
                         BindingResult bindingResult,
                         @ModelAttribute("user") UserLoginDto user,
                         Model model) {
        if (bindingResult.hasErrors()) {
            getLocationsForIndex(user, model);
            return "index";
        }
        List<OpenWeatherLocationsResponseDto> locationsByCityName = openWeatherService.getLocationsByCityName(citySearchDto.getCityName());
        model.addAttribute("cityName", citySearchDto.getCityName());
        model.addAttribute("searchResults", locationsByCityName);
        return "search-results";
    }

    @GetMapping("/index")
    public String getIndex(@ModelAttribute("user") UserLoginDto user, Model model) {
        getLocationsForIndex(user, model);
        model.addAttribute("citySearchDto", new CitySearchDto());
        return "index";
    }

    private void getLocationsForIndex(UserLoginDto user, Model model) {
        List<LocationResponseDto> locations = locationService.getLocationsByUserId(user.id());
        if (!locations.isEmpty()) {
            List<OpenWeatherWeatherResponseDto> locationsWithWeather = openWeatherService.getWeather(locations);
            model.addAttribute("locations", locationsWithWeather);
        }
    }

    @PostMapping("/locations")
    public String saveLocation(@ModelAttribute("location") LocationRequestDto locationDto,
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
