package com.chuckcha.weatherapp.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CitySearchDto {

    @NotBlank(message = "City name is required")
    @Pattern(regexp = "^[A-Za-z\\s-]+$", message = "Please enter city name in English")
    private String cityName;
}
