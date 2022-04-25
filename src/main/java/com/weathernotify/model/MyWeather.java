package com.weathernotify.model;

import lombok.Data;

import java.util.Date;

@Data
public class MyWeather {


    private String locationName;



    private String  startTime;
    private String  endTime;
    private String  sky;
    private Integer skyImgNumber;
    private Integer rain;
    private Integer minT;
    private Integer maxT;
    private String  feel;




}
