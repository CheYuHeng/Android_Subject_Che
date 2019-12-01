package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView aqiText;
    private TextView pm25Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}
