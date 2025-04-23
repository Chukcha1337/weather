package com.chuckcha.weatherapp.service;

import com.chuckcha.weatherapp.dto.user.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.DuplicateLoginException;
import com.chuckcha.weatherapp.model.User;
import com.chuckcha.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(BCryptPasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(UserRegistrationDto userRegistrationDto) {
        String login = userRegistrationDto.getLogin();
        if (userRepository.existsByLogin(login)) {
            throw new DuplicateLoginException("Login %s already exists".formatted(login));
        }
        String encodedPassword = encoder.encode(userRegistrationDto.getPassword());
        User user = User.builder().login(login).password(encodedPassword).build();
        userRepository.save(user);
    }
}
