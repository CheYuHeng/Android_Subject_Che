package com.example.weathershow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathershow.gson.Lifestyle;
import com.example.weathershow.util.HttpUtil;
import com.example.weathershow.util.Utility;

import com.example.weathershow.gson.Weather;
import com.example.weathershow.gson.Forecast;
//import com.example.weathershow.gson.AQI;
import com.example.weathershow.gson.Basic;
import com.example.weathershow.util.HttpUtil;
import com.example.weathershow.util.Utility;
import com.example.weathershow.gson.Now;


import java.io.IOException;

import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;  //气温

    private TextView weatherInfoText;  //天气概况

    private LinearLayout forecastLayout;

    private LinearLayout lifestyleLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    public String mWeatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        //定义缓存对象
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);

        if (weatherString != null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        }
        else {
            //无缓存时去服务器查询天气信息
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    /*根据天气ID请求天气信息*/
    private void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.net/s6/weather/?location="+
                weatherId+"&key=27adedc1784c4b6da58b7b22e677a031";

//        String weatherUrl_2 = "https://free-api.heweather.net/s6/weather/lifestyle?location="+
//                weatherId+"&key=27adedc1784c4b6da58b7b22e677a031";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"从网上获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    //缓存数据下处理并展示Weather实体类中的数据
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.updateTime.split(" ")[1]; //split：分解
//        String degree = weather.now.temperature+"°C";
//        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
//        degreeText.setText(degree);
//        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView)view.findViewById(R.id.data_text);
            TextView Wind_Inf = (TextView)view.findViewById(R.id.wind_dir);
            TextView Day_Inf = (TextView)view.findViewById(R.id.cond_day);
            TextView Night_Inf = (TextView)view.findViewById(R.id.cond_night);
            dateText.setText(forecast.date);
            Wind_Inf.setText(forecast.windDir);
            Day_Inf.setText(forecast.condDay);
            Night_Inf.setText(forecast.condNight);
            forecastLayout.addView(view);
        }

        //获取有关生活建议方面的信息，例如舒适度指数等
        lifestyleLayout.removeAllViews();
        for(Lifestyle lifestyle : weather.lifestyleList){
            View view = LayoutInflater.from(this).inflate(R.layout.lifestyle_item,lifestyleLayout,false);
            TextView Comf = (TextView)view.findViewById(R.id.comf);
            TextView type = (TextView)view.findViewById(R.id.type);
            TextView brf = (TextView)view.findViewById(R.id.brf);
            TextView txt = (TextView)view.findViewById(R.id.txt);
            Comf.setText(lifestyle.comf);
            type.setText(lifestyle.type);
            brf.setText(lifestyle.brf);
            txt.setText(lifestyle.txt);
            lifestyleLayout.addView(view);
        }

//        if (weather.aqi != null){
//            aqiText.setText(weather.aqi.city.aqi);
//            pm25Text.setText(weather.aqi.city.pm25);
//        }
//        String comfort = "舒适度：" +weather.suggestion.comfort.info;
//        String carWash = " 洗车指数：" +weather.suggestion.carWash.info;
//        String sport = "运动建议：" +weather.suggestion.sport.info;
//        comfortText.setText(comfort);
//        carWashText.setText(carWash);
//        sportText.setText(sport);

        weatherLayout.setVisibility(View.VISIBLE);
    }

    //初始化控件
    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
//        degreeText = (TextView)findViewById(R.id.degree_text);
//        weatherInfoText = (TextView)findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);

        lifestyleLayout = (LinearLayout)findViewById(R.id.lifestyle_layout);

        aqiText = (TextView)findViewById(R.id.aqi_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
    }
}
