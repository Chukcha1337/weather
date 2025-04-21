package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.exception.InvalidSessionIdException;
import com.chuckcha.weatherapp.exception.SessionNotFoundException;
import com.chuckcha.weatherapp.exception.SessionTimeoutException;
import com.chuckcha.weatherapp.model.Session;
import com.chuckcha.weatherapp.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService (SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public UserLoginDto getUserBySessionId(String sessionId) {
        try {
            UUID uuid = UUID.fromString(sessionId);
            Session session = sessionRepository.findSessionWithUserById(uuid)
                    .orElseThrow(() -> new SessionNotFoundException(("Session '%s' not found".formatted(sessionId))));
            UserLoginDto userLoginDto = new UserLoginDto(session.getUser().getId(), session.getUser().getLogin());
            LocalDateTime expiresAt = session.getExpiresAt();
            if (expiresAt.isBefore(LocalDateTime.now())) {
                sessionRepository.deleteById(uuid);
                throw new SessionTimeoutException("Session is over");
            }
            return userLoginDto;
        } catch (IllegalArgumentException e) {
            throw new InvalidSessionIdException("Invalid session ID: '%s'".formatted(sessionId));
        }
    }
}

