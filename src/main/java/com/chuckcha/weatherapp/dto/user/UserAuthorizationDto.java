package com.chuckcha.weatherapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class UserAuthorizationDto {

    @Email(message = "Enter valid email, please")
    @NotBlank(message = "Email cannot be empty")
    @Size(min = 6, max = 25, message = "Email should be between 6 and 25 characters")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 25, message = "Password should be between 6 and 25 characters")
    private String password;
}
