package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.location.LocationResponseDto;
import com.chuckcha.weatherapp.dto.location.LocationRequestDto;
import com.chuckcha.weatherapp.exception.DuplicateLocationException;
import com.chuckcha.weatherapp.exception.UserNotFoundException;
import com.chuckcha.weatherapp.model.Location;
import com.chuckcha.weatherapp.model.User;
import com.chuckcha.weatherapp.repository.LocationRepository;
import com.chuckcha.weatherapp.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public LocationService(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<LocationResponseDto> getLocationsByUserId(int userId) {
        return locationRepository.findAllByUserId(userId)
                .stream()
                .map(location -> new LocationResponseDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude()))
                .toList();
    }

    @Transactional
    public void saveLocation(LocationRequestDto locationDto, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s is not found".formatted(userId)));
        Location location = Location.builder()
                .name(locationDto.name())
                .user(user)
                .latitude(locationDto.lat())
                .longitude(locationDto.lon())
                .build();
        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLocationException("Location already exists");
        }
    }

    @Transactional
    public void delete(int locationId) {
        locationRepository.deleteById(locationId);
    }
}
