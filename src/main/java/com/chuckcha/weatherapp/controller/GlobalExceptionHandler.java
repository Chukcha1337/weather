package com.chuckcha.weatherapp.controller;

import com.chuckcha.weatherapp.exception.DuplicateLocationException;
import com.chuckcha.weatherapp.exception.DuplicateLoginException;
import com.chuckcha.weatherapp.exception.InvalidSessionIdException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String handleBadRequest(Throwable e, Model model) {
        String name = e.getClass().getSimpleName();
        model.addAttribute("name", name);
        model.addAttribute("message", e.getMessage());

        int code;
        switch (name) {
            case "DuplicateLocationException":
                code = HttpServletResponse.SC_BAD_REQUEST;
                break;
            case "SessionNotFoundException":
                code = HttpServletResponse.SC_NOT_FOUND;
                break;
            default:
                code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        model.addAttribute("code", code);
        return "error";
    }
}
