package service;

import com.chuckcha.weatherapp.dto.user.UserAuthorizationDto;
import com.chuckcha.weatherapp.dto.user.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.SessionTimeoutException;
import com.chuckcha.weatherapp.model.Session;
import com.chuckcha.weatherapp.model.User;
import com.chuckcha.weatherapp.repository.SessionRepository;
import com.chuckcha.weatherapp.repository.UserRepository;
import com.chuckcha.weatherapp.service.AuthorizationService;
import com.chuckcha.weatherapp.service.RegistrationService;
import com.chuckcha.weatherapp.service.SessionService;
import config.TestConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorizationServiceTest {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private RegistrationService registrationService;

    private final String login = "valid@email.com";
    private final String password = "password";
    private final UserRegistrationDto registrationUserDto = new UserRegistrationDto(login, password);
    private final UserAuthorizationDto authorizationUserDto = new UserAuthorizationDto(login, password);
    @Autowired
    private UserRepository userRepository;

    @Test
    void expiredSessionThrowsException() {
        registrationService.save(registrationUserDto);
        User user = userRepository.findByLogin(registrationUserDto.getLogin()).orElse(null);
        assertNotNull(user);
        UUID sessionId = UUID.randomUUID();
        Session session = Session.builder()
                .id(sessionId)
                .user(user)
                .expiresAt(LocalDateTime.now().minusHours(1))
                .build();
        sessionRepository.save(session);
        String sessionIdStr = session.getId().toString();
        assertThrows(SessionTimeoutException.class, () -> sessionService.getUserBySessionId(sessionIdStr));
    }

    @Test
    void isUserAuthorized() {
        registrationService.save(registrationUserDto);
        assertDoesNotThrow(() -> authorizationService.authorizeUser(authorizationUserDto));
    }
}
