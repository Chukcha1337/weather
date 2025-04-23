package com.chuckcha.weatherapp.dto.location;

import java.math.BigDecimal;

public record LocationRequestDto(String name, BigDecimal lat, BigDecimal lon) {
}
