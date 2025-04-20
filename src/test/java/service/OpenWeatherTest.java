package service;

import com.chuckcha.weatherapp.api.OpenWeatherClient;
import com.chuckcha.weatherapp.dto.weather.OpenWeatherLocationsResponseDto;
import config.TestConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OpenWeatherTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Test
    void shouldReturnLocationList() {
        String jsonResponse = """
        [
          {
            "name": "London",
            "lat": 51.5074,
            "lon": -0.1278,
            "country": "GB"
          }
        ]
    """;
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=someKey";

        when(restTemplate.getForObject(url, OpenWeatherLocationsResponseDto[].class).thenReturn();
        List<OpenWeatherLocationsResponseDto> london = openWeatherClient.getLocationsByCityName("London");


    }


}
