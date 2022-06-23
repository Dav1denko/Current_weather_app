package com.example.Current_weather_app.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherCity {

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("main")
    @Expose
    private MainValues mainValues;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("timezone")
    @Expose
    private int timezone;

    public List<Weather> getWeather() {
        return weather;
    }

    public MainValues getMainValues() {
        return mainValues;
    }

    public Wind getWind() {
        return wind;
    }

    public int getDt() {
        return dt;
    }

    public int getTimezone() {
        return timezone;
    }

}
