package com.example.weatherapp;

import android.app.Activity;

/**
 * Created by nish on 7/11/16.
 */
public interface Presenter {

    void takeView(WeatherAppView view);

    void update();

    void onCreate();
}
