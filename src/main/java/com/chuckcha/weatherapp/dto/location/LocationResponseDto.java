package com.chuckcha.weatherapp.dto.location;

import java.math.BigDecimal;

public record LocationResponseDto(int id, String name, BigDecimal lat, BigDecimal lon) {
}
