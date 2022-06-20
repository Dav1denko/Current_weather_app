package com.example.Current_weather_app.Converters;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

    public String dateForAdapter(int dt, int timezone){
        Date date = new Date((dt + timezone) * 1000L);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm \n dd:MM:yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

}
