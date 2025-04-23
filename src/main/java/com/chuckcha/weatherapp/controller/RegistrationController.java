package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.dto.user.UserRegistrationDto;
import com.chuckcha.weatherapp.exception.DuplicateLoginException;
import com.chuckcha.weatherapp.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            registrationService.save(userRegistrationDto);
        } catch (DuplicateLoginException e) {
            bindingResult.rejectValue("login", "error.login", e.getMessage());
            return "registration";
        }
            return "redirect:/authorization";
        }
    }
