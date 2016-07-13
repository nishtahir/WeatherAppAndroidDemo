package com.example.weatherapp.api;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    String baseUrl;

    public NetworkModule(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    OkHttpClient clientBuilderProvider() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit retrofitProvider(OkHttpClient client) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(WeatherService.OpenWeatherMapAPI.BASE)
                        .addConverterFactory(GsonConverterFactory.create()).client(client);
        return builder.build();

    }


    @Provides
    WeatherService serviceProvider(Retrofit retrofit){
        WeatherService.OpenWeatherMapAPI api = retrofit.create(WeatherService.OpenWeatherMapAPI.class);
        return new WeatherService(api);
    }

}
