package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LogoutService {

    private final SessionRepository sessionRepository;

    public LogoutService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public void logout(String sessionId) {
        UUID uuid = UUID.fromString(sessionId);
        sessionRepository.deleteById(uuid);
    }
}
