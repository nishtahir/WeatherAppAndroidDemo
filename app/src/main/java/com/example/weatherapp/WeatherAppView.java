package com.example.weatherapp;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Forecast;

/**
 * Created by nish on 7/11/16.
 */
public interface WeatherAppView {

    void showProgress();

    void hideProgress();

    String getCity();

    void setCurrentWeather(CurrentWeather currentWeather);

    void setForecast(Forecast forecast);

    void displayError(String message);
}
