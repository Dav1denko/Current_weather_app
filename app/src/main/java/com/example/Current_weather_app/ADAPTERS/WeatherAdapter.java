package com.example.Current_weather_app.ADAPTERS;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Current_weather_app.Converters.DateConverter;
import com.example.Current_weather_app.POJO.Main;
import com.example.Current_weather_app.POJO.NameSity;
import com.example.Current_weather_app.POJO.Weather;
import com.example.Current_weather_app.POJO.WeatherSity;
import com.example.Current_weather_app.POJO.Wind;
import com.example.Current_weather_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{


    private NameSity nameSity;
    private OnClickListener onClickListener;
    private Main mains;
    private Wind winds;
    private List<Weather> weathers;
    private WeatherSity weatherSityForDate;

    public NameSity getNameSity() {
        return nameSity;
    }

    public void setNameSity(NameSity nameSity) {
        this.nameSity = nameSity;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public WeatherSity getWeatherSityForDate() {
        return weatherSityForDate;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWeatherSityForDate(WeatherSity weatherSityForDate) {
        this.weatherSityForDate = weatherSityForDate;
        notifyDataSetChanged();
    }

    public Main getMains() {
        return mains;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMains(Main mains) {
        this.mains = mains;
        notifyDataSetChanged();
    }

    public Wind getWinds() {
        return winds;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWinds(Wind winds) {
        this.winds = winds;
        notifyDataSetChanged();
    }

    public List<Weather> getWeathers() {
        return weathers;
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
        .resize(700,700)
        .into(holder.imageViewWeatherStatus);
holder.textViewNameDescription.setText(weather.getDescription());
holder.textViewNameTemperature.setText(String.format("%.0f",mains.getTemp()));
holder.textViewHumidity.setText(Integer.toString(mains.getHumidity()));
holder.textViewWindSpeed.setText(String.format("%.0f",winds.getSpeed()));
holder.textViewNameDate.setText(dateConverter.dateForAdapter(weatherSityForDate.getDt(),
        weatherSityForDate.getTimezone()));
holder.textViewNameCity.setText(nameSity.getNameSity());
holder.textViewNameCity.setVisibility(View.VISIBLE);
holder.textViewChangeNameCity.setVisibility(View.GONE);
holder.textViewChangeNameCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    nameSity.setNameSity(holder.textViewChangeNameCity.getText().toString());
                    onClickListener.onGetNameSityClick(nameSity.getNameSity());
                    holder.textViewNameCity.setVisibility(View.VISIBLE);
                    holder.textViewChangeNameCity.setVisibility(View.GONE);
                    onClickListener.onShowEditTextClick(view.getId());
                    holder.textViewChangeNameCity.setText("");
                    return true;
                }
                return false;
            }
        });


    }
    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewChangeNameCity;
        private TextView textViewNameTemperature;
        private TextView textViewNameDate;
        private TextView textViewNameDescription;
        private ImageView imageViewWeatherStatus;
        private TextView textViewNameCity;
        private TextView textViewHumidity;
        private TextView textViewWindSpeed;



        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChangeNameCity = itemView.findViewById(R.id.TextView_ChangeNameCity);
            textViewNameTemperature = itemView.findViewById(R.id.textView_Temperature);
            textViewNameDescription = itemView.findViewById(R.id.textView_Description);
            imageViewWeatherStatus = itemView.findViewById(R.id.imageView_WeatherStatus);
            textViewNameDate = itemView.findViewById(R.id.textView_Date);
            textViewWindSpeed = itemView.findViewById(R.id.textView_windSpeed);
            textViewHumidity = itemView.findViewById(R.id.textView_Humidity);
            textViewNameCity = itemView.findViewById(R.id.textView_NameCity);
            textViewNameCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onClickListener != null){
                        textViewNameCity.setVisibility(View.GONE);
                        textViewChangeNameCity.setVisibility(View.VISIBLE);
                        textViewChangeNameCity.requestFocus();
                       onClickListener.onShowEditTextClick(view.getId());

                }
                }
            });


                           }



        }
    }

