package com.example.Current_weather_app.API;

import com.example.Current_weather_app.POJO.WeatherCity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather?")
    Observable<WeatherCity> getWeatherCity(@Query("q") String nameCity, @Query("lang") String LocaleLanguage,
                                           @Query("appid") String api_id, @Query("units") String tempInCelsius);
}
