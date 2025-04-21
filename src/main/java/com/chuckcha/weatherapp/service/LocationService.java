package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.location.LocationFromDatabaseDto;
import com.chuckcha.weatherapp.dto.location.LocationToDatabaseDto;
import com.chuckcha.weatherapp.exception.DuplicateLocationException;
import com.chuckcha.weatherapp.exception.UserNotFoundException;
import com.chuckcha.weatherapp.entity.Location;
import com.chuckcha.weatherapp.entity.User;
import com.chuckcha.weatherapp.repository.LocationRepository;
import com.chuckcha.weatherapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    @Transactional
    public List<LocationFromDatabaseDto> getLocationsByUserId(int userId) {
        return locationRepository.findAllByUserId(userId)
                .orElse(Collections.emptyList())
                .stream()
                .map(location -> new LocationFromDatabaseDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude()))
                .toList();
    }

    @Transactional
    public void saveLocation(LocationToDatabaseDto locationDto, Integer userId) {
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
