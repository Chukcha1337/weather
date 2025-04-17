package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.UserAuthorizationDto;
import com.chuckcha.weatherapp.dto.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.InvalidPasswordException;
import com.chuckcha.weatherapp.exception.UserNotFoundException;
import com.chuckcha.weatherapp.model.Session;
import com.chuckcha.weatherapp.model.User;
import com.chuckcha.weatherapp.repository.SessionRepository;
import com.chuckcha.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorizationService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public AuthorizationService(BCryptPasswordEncoder encoder, UserRepository userRepository, SessionRepository sessionRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public UUID authorizeUser(UserAuthorizationDto userAuthorizationDto) {
        User user = userRepository.findByLogin(userAuthorizationDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException(userAuthorizationDto.getLogin()));
        if (encoder.matches(userAuthorizationDto.getPassword(), user.getPassword())) {
            UUID sessionId = UUID.randomUUID();
            Session session = Session.builder()
                    .id(sessionId)
                    .user(user)
                    .expiresAt(LocalDateTime.now().plusHours(8))
                    .build();
            sessionRepository.save(session);
            return sessionId;
        } else {
            throw new InvalidPasswordException("Wrong password for user '%s'".formatted(user.getLogin()));
        }
    }
}
