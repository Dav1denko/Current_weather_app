package com.example.Current_weather_app.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainValues {
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("humidity")
    @Expose
    private int humidity;


    public double getTemp() {
        return temp;
    }


    public int getHumidity() {
        return humidity;
    }

  

}
