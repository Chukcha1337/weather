package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.dto.user.UserAuthorizationDto;
import com.chuckcha.weatherapp.dto.user.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.InvalidPasswordException;
import com.chuckcha.weatherapp.exception.UserNotFoundException;
import com.chuckcha.weatherapp.service.AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public String getAuthorizationForm(Model model) {
        model.addAttribute("userAuthorizationDto", new UserRegistrationDto());
        return "authorization";
    }

    @PostMapping
    public String authorizeUser(@ModelAttribute("userAuthorizationDto") @Valid UserAuthorizationDto userAuthorizationDto,
                                BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "authorization";
        }
        try {
            UUID uuid = authorizationService.authorizeUser(userAuthorizationDto);
            Cookie cookie = new Cookie("session_id", uuid.toString());
            response.addCookie(cookie);
        } catch (UserNotFoundException e) {
            bindingResult.rejectValue("login", "error.login", e.getMessage());
            return "authorization";
        } catch (InvalidPasswordException e) {
            bindingResult.rejectValue("password", "error.password", e.getMessage());
            return "authorization";
        }
        return "redirect:/index";
    }
}
