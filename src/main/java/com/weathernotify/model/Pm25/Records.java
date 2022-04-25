package com.weathernotify.model.Pm25;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Records {

    @JsonProperty("Site")
    private String site;

    private String county;

    @JsonProperty("PM25")
    private String pm25;

    @JsonProperty("DataCreationDate")
    private String dataCreationDate;

    @JsonProperty("ItemUnit")
    private String itemUnit;
}
