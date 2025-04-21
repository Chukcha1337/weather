package com.chuckcha.weatherapp.repository;

import com.chuckcha.weatherapp.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.user.id = :userId")
    Optional<List<Location>> findAllByUserId(@Param("userId") Integer userId);
}
