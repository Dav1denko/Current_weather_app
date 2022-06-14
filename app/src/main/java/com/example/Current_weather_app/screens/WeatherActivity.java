package com.example.Current_weather_app.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.Current_weather_app.ADAPTERS.OnClickListener;
import com.example.Current_weather_app.ADAPTERS.WeatherAdapter;
import com.example.Current_weather_app.BuildConfig;
import com.example.Current_weather_app.POJO.Main;
import com.example.Current_weather_app.POJO.NameSity;
import com.example.Current_weather_app.POJO.Weather;
import com.example.Current_weather_app.POJO.WeatherSity;
import com.example.Current_weather_app.R;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity implements WeatherView {
    private RecyclerView recyclerViewWeather;
    private WeatherAdapter adapter;
    private WeatherPresenter presenter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new WeatherPresenter(this);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeatherCity);

        adapter = new WeatherAdapter();
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWeather.setAdapter(adapter);
        adapter.setMains(new Main());
        adapter.setWeatherSityForDate(new WeatherSity());
        adapter.setWeathers(new ArrayList<Weather>());
        presenter.checkLocaleLanguage();
        presenter.loadNameSity();
        presenter.loadData();

        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onShowEditTextClick(long id) {
                InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, 0);

            }

            @Override
            public void onGetNameSityClick(String nameSity) {
                if (nameSity.length() != 0){
                presenter.ChangeNameSityForAdapter(nameSity);
                presenter.loadData();}
                else {
                    Toast.makeText(WeatherActivity.this, "Ошибка, введите город", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }



    @Override
    public void showData(WeatherSity weatherSity) {
adapter.setMains(weatherSity.getMain());
adapter.setWeathers(weatherSity.getWeather());
adapter.setWeatherSityForDate(weatherSity);

    }

    @Override
    public void showName(NameSity nameSity) {
        adapter.setNameSity(nameSity);
    }

    @Override
    public void showError() {
        String apiKey = BuildConfig.API_KEY;

        Toast.makeText(this, "Ошибка" + apiKey, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
presenter.disposeDisposable();


        super.onDestroy();
    }
}