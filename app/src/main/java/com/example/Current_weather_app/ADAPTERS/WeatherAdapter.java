package com.example.Current_weather_app.ADAPTERS;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Current_weather_app.Converters.DateConverter;
import com.example.Current_weather_app.POJO.MainValues;
import com.example.Current_weather_app.POJO.CityName;
import com.example.Current_weather_app.POJO.Weather;
import com.example.Current_weather_app.POJO.WeatherCity;
import com.example.Current_weather_app.POJO.Wind;
import com.example.Current_weather_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{


    private CityName cityName;
    private OnClickListener onClickListener;
    private MainValues mainValues;
    private Wind winds;
    private List<Weather> weathers;
    private WeatherCity weatherCityForDate;



    @SuppressLint("NotifyDataSetChanged")
    public void setCityName(CityName cityName) {
        this.cityName = cityName;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setWeatherCityForDate(WeatherCity weatherCityForDate) {
        this.weatherCityForDate = weatherCityForDate;
        notifyDataSetChanged();
    }



    @SuppressLint("NotifyDataSetChanged")
    public void setMainValues(MainValues mainValues) {
        this.mainValues = mainValues;
        notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setWinds(Wind winds) {
        this.winds = winds;
        notifyDataSetChanged();
    }



    @SuppressLint("NotifyDataSetChanged")
    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);
        return new WeatherViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
Weather weather = weathers.get(position);
DateConverter dateConverter = new DateConverter();
final String IconURL = "http://openweathermap.org/img/wn/%s@2x.png";
Picasso.get().load(String.format(IconURL, weather.getIcon()))
        .resize(600,600)
        .into(holder.imageViewWeatherStatus);
holder.textViewNameDescription.setText(weather.getDescription());
holder.textViewNameTemperature.setText(String.format("%.0f", mainValues.getTemp()));
holder.textViewHumidity.setText(Integer.toString(mainValues.getHumidity()));
holder.textViewWindSpeed.setText(String.format("%.0f",winds.getSpeed()));
holder.textViewNameDate.setText(dateConverter.dateForAdapter(weatherCityForDate.getDt(),
        weatherCityForDate.getTimezone()));
holder.textViewNameCity.setText(cityName.getCityName());
holder.textViewNameCity.setVisibility(View.VISIBLE);
holder.textViewChangeNameCity.setVisibility(View.GONE);
holder.textViewChangeNameCity.setOnKeyListener((view, i, keyEvent) -> {
    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
            (i == KeyEvent.KEYCODE_ENTER)) {
        cityName.setCityName(holder.textViewChangeNameCity.getText().toString().trim());
        onClickListener.onGetCityNameClick(cityName.getCityName());
        holder.textViewNameCity.setVisibility(View.VISIBLE);
        holder.textViewChangeNameCity.setVisibility(View.GONE);
        onClickListener.onShowKeyBoardClick(view.getId());
        holder.textViewChangeNameCity.setText("");
        return true;
    }
    return false;
});


    }
    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewChangeNameCity;
        private final TextView textViewNameTemperature;
        private final TextView textViewNameDate;
        private final TextView textViewNameDescription;
        private final ImageView imageViewWeatherStatus;
        private final TextView textViewNameCity;
        private final TextView textViewHumidity;
        private final TextView textViewWindSpeed;



        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChangeNameCity = itemView.findViewById(R.id.TextView_ChangeCityName);
            textViewNameTemperature = itemView.findViewById(R.id.textView_SetTemperature);
            textViewNameDescription = itemView.findViewById(R.id.textView_SetDescription);
            imageViewWeatherStatus = itemView.findViewById(R.id.imageView_WeatherStatus);
            textViewNameDate = itemView.findViewById(R.id.textView_SetDate);
            textViewWindSpeed = itemView.findViewById(R.id.textView_SetWindSpeed);
            textViewHumidity = itemView.findViewById(R.id.textView_SetHumidity);
            textViewNameCity = itemView.findViewById(R.id.textView_CityName);
            textViewNameCity.setOnClickListener(view -> {
                if (onClickListener != null){
                    textViewNameCity.setVisibility(View.GONE);
                    textViewChangeNameCity.setVisibility(View.VISIBLE);
                    textViewChangeNameCity.requestFocus();
                   onClickListener.onShowKeyBoardClick(view.getId());

            }
            });


                           }



        }
    }

