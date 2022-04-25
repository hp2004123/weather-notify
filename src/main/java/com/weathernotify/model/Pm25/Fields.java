package com.weathernotify.model.Pm25;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Fields {

        private String type;
        private String id;
        private Info info;

}
