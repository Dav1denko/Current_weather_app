package com.example.Current_weather_app.screens;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.Current_weather_app.API.ApiFactory;
import com.example.Current_weather_app.API.ApiService;
import com.example.Current_weather_app.BuildConfig;
import com.example.Current_weather_app.POJO.MyApplication;
import com.example.Current_weather_app.POJO.NameSity;
import com.example.Current_weather_app.POJO.WeatherSity;

import java.util.Locale;

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


   private final NameSity nameSity = new NameSity();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());

   public void ChangeNameSityForAdapter(String nameSityFromAdapter){
       nameSity.setNameSity(nameSityFromAdapter);
       preferences.edit().putString("SaveNameSity",nameSity.getNameSity()).apply();
       weatherView.showName(nameSity);
   }

    public void loadNameSity(){
       String SaveNameSity = preferences.getString("SaveNameSity","Лондон");
       nameSity.setNameSity(SaveNameSity);
        weatherView.showName(nameSity);
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
        Disposable disposable = apiService.getWeatherSity(nameSity.getNameSity(),checkLocaleLanguage(),apiKey,tempInCelsius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherSity>(){
                               @Override
                               public void accept(WeatherSity weatherSity) throws Exception {
weatherView.showData(weatherSity);

                               }
                           },new Consumer<Throwable>(){
                               @Override
                               public void accept(Throwable throwable) throws Exception {
weatherView.showError();
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
