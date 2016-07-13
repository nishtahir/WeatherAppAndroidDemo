package com.example.weatherapp.api;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Forecast;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Weather service service
 */
public class WeatherService {

    /**
     * Weather api
     */
    private OpenWeatherMapAPI openWeatherMapAPI;

    public WeatherService() {
        openWeatherMapAPI = ServiceFactory.createService(OpenWeatherMapAPI.class);
    }

    /**
     * @param location         Where to find the thing.
     * @param forecastCallback Thing to do with the thing that you get from the thing.
     */
    public void loadForecast(String location, Callback<Forecast> forecastCallback) {
        Call<Forecast> test = openWeatherMapAPI.loadForecast(location);
        test.enqueue(forecastCallback);
    }

    /**
     * Current whether information.
     * @param location where
     * @param currentWeatherCallback
     */
    public void loadCurrent(String location, Callback<CurrentWeather> currentWeatherCallback) {
        Call<CurrentWeather> test = openWeatherMapAPI.loadCurrent(location);
        test.enqueue(currentWeatherCallback);
    }

    public interface OpenWeatherMapAPI {

        String BASE = "http://api.openweathermap.org/";

        String APIKEY = "f8fdae74c29544baebdb927d392c5538";

        String units = "metric";

        /**
         * Ref: http://openweathermap.org/forecast16
         * @param location City name and/or country code
         */
        @GET("/data/2.5/forecast/daily?mode=json&units="+ units +"&cnt=14&appid=" + APIKEY)
        Call<Forecast> loadForecast(@Query("q") String location);

        /**
         * Ref: http://openweathermap.org/current
         * @param location City name and/or country code
         */
        @GET("/data/2.5/weather?mode=json&units="+ units +"&appid=" + APIKEY)
        Call<CurrentWeather> loadCurrent(@Query("q") String location);
    }
}
