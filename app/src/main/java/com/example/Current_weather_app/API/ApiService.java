package com.example.Current_weather_app.API;

import com.example.Current_weather_app.POJO.WeatherSity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather?")
   Observable <WeatherSity> getWeatherSity(@Query("q") String nameSity,@Query("lang") String LocaleLanguage,
                                           @Query("appid") String api_id,@Query("units") String tempInCelsius);

}
