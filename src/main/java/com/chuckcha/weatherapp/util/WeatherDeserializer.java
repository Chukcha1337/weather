package com.chuckcha.weatherapp.util;

import com.chuckcha.weatherapp.dto.weather.OpenWeatherWeatherResponseDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class WeatherDeserializer extends JsonDeserializer<OpenWeatherWeatherResponseDto> {

    @Override
    public OpenWeatherWeatherResponseDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode weatherNode = node.get("weather").get(0);
        JsonNode mainNode = node.get("main");
        JsonNode sysNode = node.get("sys");
        String icon = weatherNode.get("icon").asText();
        String temp = Math.round(mainNode.get("temp").asDouble()) + " °C";
        String country = CountryUtils.convertCodeToName(sysNode.get("country").asText());
        String feelsLike = Math.round(mainNode.get("feels_like").asDouble()) + " °C";
        String description = weatherNode.get("description").asText();
        int humidity = mainNode.get("humidity").asInt();
        return new OpenWeatherWeatherResponseDto(icon, temp, country, feelsLike, description, humidity);
    }
}
