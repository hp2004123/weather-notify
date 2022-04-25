package com.weathernotify.model.Pm25;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Info {
    private  String notes;
    private  String label;
}
