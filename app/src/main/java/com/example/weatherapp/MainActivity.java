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
import android.widget.Toast;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.model.WeatherData;
import com.example.weatherapp.api.WeatherService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements WeatherAppView{

    private EditText editText;

    private WeatherDataAdapter weatherDataAdapter;

    private WeatherAppPresenter weatherAppPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        weatherAppPresenter = new WeatherAppPresenter(new WeatherService());
        weatherAppPresenter.takeView(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherAppPresenter.update();
            }
        });

        editText = (EditText) findViewById(R.id.enter_city);
        weatherDataAdapter = new WeatherDataAdapter(this);

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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getCity() {
        return editText.getText().toString();
    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        weatherDataAdapter.setCurrentWeather(currentWeather);
    }

    @Override
    public void setForecast(Forecast forecast) {
        weatherDataAdapter.setForecast(forecast);
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
