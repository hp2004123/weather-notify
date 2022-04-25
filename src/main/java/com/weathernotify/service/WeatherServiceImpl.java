package com.weathernotify.service;

import com.weathernotify.dao.WeatherDao;
import com.weathernotify.model.MyWeather;
import com.weathernotify.model.Pm25.Pm25;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherDao weatherDao;

    @Override
    public Pm25 getPm25() {
        return weatherDao.getPm25();
    }

    @Override
    public List<MyWeather> getWeather() {
        return weatherDao.getWeather();
    }

    @Override
    public ResponseEntity lineNotify(List<MyWeather> list, Pm25 pm25) {
        return weatherDao.lineNotify(list, pm25);
    }
}
