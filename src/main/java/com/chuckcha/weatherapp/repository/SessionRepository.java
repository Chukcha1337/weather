package com.chuckcha.weatherapp.repository;

import com.chuckcha.weatherapp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("SELECT s FROM Session s JOIN FETCH s.user WHERE s.id = :id")
    Optional<Session> findSessionWithUserById(@Param("id") UUID uuid);
}
