package com.example.Current_weather_app.screens;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.Current_weather_app.API.ApiFactory;
import com.example.Current_weather_app.API.ApiService;
import com.example.Current_weather_app.BuildConfig;
import com.example.Current_weather_app.POJO.MyApplication;
import com.example.Current_weather_app.POJO.CityName;
import com.example.Current_weather_app.POJO.WeatherCity;

import java.util.Locale;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {


    private CompositeDisposable compositeDisposable;
    private WeatherView weatherView;
    private final String apiKey = BuildConfig.API_KEY;
    private final String tempInCelsius = "metric";
    private final String ErrorInternet = "Unable to resolve host \"api.openweathermap.org\": No address associated with hostname";


   private final CityName cityName = new CityName();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());

   public void ChangeNameCityForAdapter(String nameCityFromAdapter){
       cityName.setCityName(nameCityFromAdapter);
       preferences.edit().putString("SaveNameCity", cityName.getCityName()).apply();
       weatherView.showName(cityName);
   }

    public void loadNameCity(){
       String SaveNameCity = preferences.getString("SaveNameCity","London");
       cityName.setCityName(SaveNameCity);
        weatherView.showName(cityName);
    }
public String checkLocaleLanguage(){
       String LocaleLanguage;
       if (Locale.getDefault().getLanguage().equals("ru")){
       LocaleLanguage = "ru";}
       else { LocaleLanguage = "eng";}
       return LocaleLanguage;

}
    public WeatherPresenter(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void loadData(){
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getWeatherCity(cityName.getCityName(),checkLocaleLanguage(),apiKey,tempInCelsius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherCity>(){
                               @Override
                               public void accept(WeatherCity weatherCity) throws Exception {
weatherView.showData(weatherCity);

                               }
                           },new Consumer<Throwable>(){
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   if(Objects.equals(throwable.getMessage(), ErrorInternet))
                                   {weatherView.showErrorInternet();

                                   }else if(Objects.equals(throwable.getMessage(), "HTTP 404 Not Found")){
                                       preferences.edit().clear().apply();
                                       weatherView.showErrorNameCity();

                                   }



                               }
                           }
                );
        compositeDisposable.add(disposable);





    }
    public void disposeDisposable(){
        if (compositeDisposable!=null){
        compositeDisposable.dispose();}
    }
}
