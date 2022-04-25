package com.weathernotify.dao;

import com.weathernotify.model.MyWeather;
import com.weathernotify.model.Pm25.Pm25;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WeatherDao {

    public Pm25 getPm25();
    public List<MyWeather> getWeather();
    public ResponseEntity lineNotify(List<MyWeather> list, Pm25 pm25);
}
