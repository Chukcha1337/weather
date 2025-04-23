package com.chuckcha.weatherapp.controller.interceptor;

import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import com.chuckcha.weatherapp.exception.SessionNotFoundException;
import com.chuckcha.weatherapp.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    public SessionInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String sessionIdString = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("session_id"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseThrow(() -> new SessionNotFoundException("Invalid session ID"));
            UserLoginDto userLoginDto = sessionService.getUserBySessionId(sessionIdString);
            request.setAttribute("user", userLoginDto);
            return true;
        } else {
            response.sendRedirect("/authorization");
            return false;
        }
    }
}
