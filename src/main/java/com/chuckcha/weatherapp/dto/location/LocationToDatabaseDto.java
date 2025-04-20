package com.chuckcha.weatherapp.dto.location;

import java.math.BigDecimal;

public record LocationToDatabaseDto(String name, BigDecimal lat, BigDecimal lon) {
}
