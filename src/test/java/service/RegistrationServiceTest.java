package service;

import com.chuckcha.weatherapp.dto.user.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.DuplicateLoginException;
import com.chuckcha.weatherapp.entity.User;
import com.chuckcha.weatherapp.repository.UserRepository;
import com.chuckcha.weatherapp.service.RegistrationService;
import config.TestConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceTest {

    private final UserRegistrationDto validUser = new UserRegistrationDto("valid@email.com", "password");

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void isValidUserSaved() {
        registrationService.save(validUser);
        Optional<User> byLogin = userRepository.findByLogin(validUser.getLogin());
        assertTrue(byLogin.isPresent());
    }

    @Test
    void isDuplicatedUserNotSaved() {
        registrationService.save(validUser);
        assertThrows(DuplicateLoginException.class, () -> registrationService.save(validUser));
    }
}
