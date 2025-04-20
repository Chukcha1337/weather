package com.chuckcha.weatherapp.dto.location;

import java.math.BigDecimal;

public record LocationFromDatabaseDto(int id, String name, BigDecimal lat, BigDecimal lon) {
}
