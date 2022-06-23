package com.example.Current_weather_app.screens;

import com.example.Current_weather_app.POJO.CityName;
import com.example.Current_weather_app.POJO.WeatherCity;

public interface WeatherView {

    void showData(WeatherCity weatherCity);
    void showErrorInternet();
    void showName(CityName cityName);
    void showErrorNameCity();
}
