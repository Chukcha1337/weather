package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.exception.InvalidSessionIdException;
import com.chuckcha.weatherapp.exception.SessionNotFoundException;
import com.chuckcha.weatherapp.exception.SessionTimeoutException;
import com.chuckcha.weatherapp.model.Session;
import com.chuckcha.weatherapp.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService (SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Expired sessions deletion started at {}", now);
        int deleted = sessionRepository.deleteByExpiresAtBefore(now);
        log.info("{} sessions deleted", deleted);
        log.info("Expired sessions deletion finished at {}", LocalDateTime.now());
    }
}


