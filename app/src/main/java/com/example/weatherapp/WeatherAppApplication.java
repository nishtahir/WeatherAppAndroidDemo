package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.api.DaggerNetworkComponent;
import com.example.weatherapp.api.NetworkComponent;
import com.example.weatherapp.api.NetworkModule;
import com.example.weatherapp.api.WeatherService;

/**
 * Application entry-point init components
 */
public class WeatherAppApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(WeatherService.OpenWeatherMapAPI.BASE))
                .build();

    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
