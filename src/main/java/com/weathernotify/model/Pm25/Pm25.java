package com.weathernotify.model.Pm25;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Pm25 {


    private String sort;

    private List<Records> records;

    @JsonProperty("include_total")
    private boolean includeTotal;

    @JsonProperty("resource_id")
    private String resourceId;

    private List<Fields> fields;
}
