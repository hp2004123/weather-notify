package com.weathernotify.model.Weather;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Weather {
    private Integer id;
    private String name;
    private String status;
    private String date;

    // 最高氣溫
    private String max;

    // 最低氣溫
    private String min;

    private LocalDateTime createTime;


    private String success;

   private  Records records;

   private Result result;



}