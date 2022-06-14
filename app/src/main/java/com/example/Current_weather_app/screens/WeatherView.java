package com.example.Current_weather_app.screens;

import com.example.Current_weather_app.POJO.NameSity;
import com.example.Current_weather_app.POJO.WeatherSity;

public interface WeatherView {

    void showData(WeatherSity weatherSity);
    void showError();
    void showName(NameSity nameSity);
}
