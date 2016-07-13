package com.example.weatherapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.model.WeatherData;
import com.example.weatherapp.service.WeatherService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    EditText editText;

    ArrayList<WeatherData> forecasts;

    WeatherDataAdapter weatherDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final WeatherService service = new WeatherService();

                String city = editText.getText().toString();

                service.loadForecast(city, new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                        forecasts.clear();
                        forecasts.addAll(response.body().weather);
                        weatherDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Forecast> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "An error occurred while loading the forcast", Toast.LENGTH_SHORT).show();
                    }
                });

                service.loadCurrent(city, new Callback<CurrentWeather>() {

                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        weatherDataAdapter.setCurrentWeather(response.body());
                        weatherDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "An error occurred while loading the current weather", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        editText = (EditText) findViewById(R.id.enter_city);
        forecasts = new ArrayList<>();
        weatherDataAdapter = new WeatherDataAdapter(this, forecasts);

        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(weatherDataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
