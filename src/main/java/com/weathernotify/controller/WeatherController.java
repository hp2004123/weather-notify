package com.weathernotify.controller;


import com.weathernotify.model.MyWeather;
import com.weathernotify.model.Pm25.Pm25;
import com.weathernotify.model.Pm25.Records;
import com.weathernotify.model.Weather.*;
import com.weathernotify.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class WeatherController {

@Autowired
private WeatherService weatherService;

    @GetMapping("/pm25")
    public  ResponseEntity<Pm25> getPm25() {

        Pm25 pm25 =  weatherService.getPm25();
        return ResponseEntity.ok(pm25);
    }




    @GetMapping("/weather")
    public ResponseEntity<List<MyWeather>> getWeather() {

        List<MyWeather> list = weatherService.getWeather();
        return ResponseEntity.ok(list);
    }








    @Scheduled(cron = "0 0 00,06,12,18 * * *")
    @GetMapping("/lineNotify")
    public ResponseEntity lineNotify() throws IOException {

        List<MyWeather> list = weatherService.getWeather();
        Pm25 pm25 = weatherService.getPm25();

       return weatherService.lineNotify(list, pm25);

    }
}



