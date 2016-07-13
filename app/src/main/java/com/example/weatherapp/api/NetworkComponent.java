package com.example.weatherapp.api;

import com.example.weatherapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(MainActivity activity);
}
