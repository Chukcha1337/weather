package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.dto.user.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @ModelAttribute("user")
    public UserLoginDto user(HttpServletRequest request) {
        return (UserLoginDto) request.getAttribute("user");
    }
}
