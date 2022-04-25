package com.weathernotify.model.Weather;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter {
    private String parameterName;
    private String parameterValue;
    private String parameterUnit;

}
