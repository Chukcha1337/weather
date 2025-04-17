package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.dto.UserAuthorizationDto;
import com.chuckcha.weatherapp.dto.UserRegistrationDto;
import com.chuckcha.weatherapp.model.User;
import com.chuckcha.weatherapp.service.AuthorizationService;
import com.chuckcha.weatherapp.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder encoder;

    public RegistrationController(RegistrationService registrationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registrationService = registrationService;
        this.encoder = bCryptPasswordEncoder;
    }

    @GetMapping("/registration")
    public String getRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.save(userRegistrationDto);
        return "redirect:/authorization";
    }
}
