package com.example.Current_weather_app.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Current_weather_app.ADAPTERS.OnClickListener;
import com.example.Current_weather_app.ADAPTERS.WeatherAdapter;
import com.example.Current_weather_app.BuildConfig;
import com.example.Current_weather_app.POJO.Main;
import com.example.Current_weather_app.POJO.NameSity;
import com.example.Current_weather_app.POJO.Weather;
import com.example.Current_weather_app.POJO.WeatherSity;
import com.example.Current_weather_app.POJO.Wind;
import com.example.Current_weather_app.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Objects;

public class WeatherActivity extends AppCompatActivity implements WeatherView {
    private RecyclerView recyclerViewWeather;
    private WeatherAdapter adapter;
    private WeatherPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerFrameLayout;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        presenter = new WeatherPresenter(this);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeatherCity);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        adapter = new WeatherAdapter();
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWeather.setAdapter(adapter);
        adapter.setMains(new Main());
        adapter.setWinds(new Wind());
        adapter.setWeatherSityForDate(new WeatherSity());
        adapter.setWeathers(new ArrayList<Weather>());
        presenter.checkLocaleLanguage();
        presenter.loadNameSity();
        presenter.loadData();
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
                    presenter.loadNameSity();
                    presenter.loadData();

                }
            }
        });






    }



    @Override
    public void showData(WeatherSity weatherSity) {
adapter.setMains(weatherSity.getMain());
adapter.setWinds(weatherSity.getWind());
adapter.setWeathers(weatherSity.getWeather());
adapter.setWeatherSityForDate(weatherSity);
shimmerFrameLayout.stopShimmer();
recyclerViewWeather.setVisibility(View.VISIBLE);

    }

    @Override
    public void showName(NameSity nameSity) {
        adapter.setNameSity(nameSity);
    }

    @Override
    public void showErrorInternet() {
        recyclerViewWeather.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();



        Toast.makeText(this, "подключите интернет и обновите", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNameSity() {
        Toast.makeText(this, "Город не найден", Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onDestroy() {
presenter.disposeDisposable();


        super.onDestroy();
    }
}