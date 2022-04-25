package com.weathernotify.model.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Records {
    private String datasetDescription;
    List<Location> location;

}
