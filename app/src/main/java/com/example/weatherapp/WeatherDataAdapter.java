package com.example.weatherapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.model.WeatherData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nish on 7/11/16.
 */
public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {

    /**
     * Gives nice formatting. Example, Tuesday 12.
     */
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE dd", Locale.US);

    private CurrentWeather currentWeather;

    private List<WeatherData> forecastData;

    Context context;


    public WeatherDataAdapter(Context context) {
        this.context = context;
        this.forecastData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherData data = forecastData.get(position);

        Drawable drawable = ContextCompat.getDrawable(context, getWeatherIcon(data.weather.get(0).main));
        holder.icon.setImageDrawable(drawable);

        holder.date.setText(dayFormat.format(new Date((long) data.timestamp * 1000)));
        holder.weather.setText(data.weather.get(0).description);
        holder.high.setText(context.getResources().getString(R.string.temperature, (int) data.temperature.max));
        holder.low.setText(context.getResources().getString(R.string.temperature, (int) data.temperature.min));

    }

    /**
     * Ref: http://openweathermap.org/weather-conditions
     *
     * @param condition weather condition category.
     * @return icon resource id
     */
    public int getWeatherIcon(String condition) {
        switch (condition.toLowerCase()) {
            case "clear":
                return R.drawable.ic_weather_sunny_24dp;
            case "thunderstorm":
                return R.drawable.ic_weather_thunderstorms_24dp;
            case "drizzle":
                return R.drawable.ic_weather_drizzle_24dp;
            case "rain":
                return R.drawable.ic_weather_heavy_rain_24dp;
            case "snow":
                return 0;
            case "clouds":
                return R.drawable.ic_weather_partly_cloudy_24dp;
            default:
                return R.drawable.ic_weather_alert_24dp;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return forecastData.size();
    }


    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
        notifyDataSetChanged();
    }

    public void setForecast(Forecast forecast) {
        forecastData.clear();
        forecastData.addAll(forecast.weather);
        notifyDataSetChanged();
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;

        TextView date;

        TextView weather;

        TextView high;

        TextView low;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.weather_icon);
            date = (TextView) itemView.findViewById(R.id.date);
            weather = (TextView) itemView.findViewById(R.id.weather_desc);
            high = (TextView) itemView.findViewById(R.id.temp_high);
            low = (TextView) itemView.findViewById(R.id.temp_low);
        }
    }
}
