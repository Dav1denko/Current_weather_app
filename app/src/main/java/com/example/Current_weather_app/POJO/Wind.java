package com.example.Current_weather_app.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    @Expose
private double speed;


    public double getSpeed() {
        return speed;
    }


}
