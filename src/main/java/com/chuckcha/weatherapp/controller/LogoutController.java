package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.service.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    private final LogoutService logoutService;

    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @PostMapping
    public String logout(@CookieValue(name = "session_id", required = false) String sessionId, HttpServletResponse response) {
        logoutService.logout(sessionId);
        Cookie cookieToRemove = new Cookie("session_id", null);
        cookieToRemove.setPath("/");
        cookieToRemove.setMaxAge(0);
        response.addCookie(cookieToRemove);

        return "redirect:/authorization";
    }
}

