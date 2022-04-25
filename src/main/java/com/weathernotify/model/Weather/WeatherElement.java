package com.weathernotify.model.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherElement {
    private String  elementName;
    private List<Time> time;
}
