package com.example.weathershow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathershow.gson.Lifestyle;
import com.example.weathershow.util.HttpUtil;
import com.example.weathershow.util.Utility;

import com.example.weathershow.gson.Weather;
import com.example.weathershow.gson.Forecast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;

public class WeatherActivity extends AppCompatActivity {

    public final String TAG = "WeatherActivity";
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;  //气温
    private TextView weatherInfoText;  //天气概况
    private LinearLayout forecastLayout;
    private LinearLayout lifestyleLayout;
//    private LinearLayout nowLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    public String mWeatherId;

    public FloatingActionButton back;
    public FloatingActionButton refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        back = (FloatingActionButton) findViewById(R.id.back);
        refresh = (FloatingActionButton) findViewById(R.id.refresh);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                WeatherActivity.this.finish();
            }
        });

        //手动刷新天气
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(WeatherActivity.this,"数据刷新成功",
                        Toast.LENGTH_SHORT).show();
                requestWeather(mWeatherId);
                Log.d(TAG,"mWeatherId的值是_2:" + mWeatherId);
                initView();
            }
        });

        initView();


        //所点击的县市的对应信息
        String weatherId = getIntent().getStringExtra("weather_id");
        weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(weatherId);

        timer.schedule(mTimerTask,5000,10000);   //每十秒自动刷新一次数据，第一次是五秒刷新


    }

    //根据天气ID请求天气信息
    private void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.net/s6/weather/?location="+
                weatherId+"&key=27adedc1784c4b6da58b7b22e677a031";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {   //如果信息获取成功
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            Log.d(TAG,"mWeatherId的值是_1:" + mWeatherId);
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this,"无法获取天气信息",
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
                        Toast.makeText(WeatherActivity.this,"无法通过网络获取天气信息",
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

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView) view.findViewById(R.id.data_text);
            TextView Wind_Inf = (TextView) view.findViewById(R.id.wind_dir);

            dateText.setText(forecast.date);
            Wind_Inf.setText(forecast.windDir);

            forecastLayout.addView(view);

            ImageView img_day = (ImageView) view.findViewById(R.id.day);    //白天天气信息所显示的图标
            ImageView img_night = (ImageView) view.findViewById(R.id.night);//夜晚天气信息所显示的图标

            Log.d(TAG,"获取forecast.condDay:" + forecast.condDay);
            Log.d(TAG,"获取forecast.condNight:" + forecast.condNight);

            if(forecast.condDay.equals("晴")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.sun));
            }
            else if(forecast.condDay.equals("多云")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.duo_yun));
            }
            else if(forecast.condDay.equals("阴")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
            }
            else if(forecast.condDay.equals("小雨")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.light_rain));
            }
            else if(forecast.condDay.equals("中雨")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.zhong_yu));
            }
            else if(forecast.condDay.equals("大雨")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.da_yu));
            }
            else if(forecast.condDay.equals("暴雨")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.bao_yu));
            }
            else if(forecast.condDay.equals("阵雨")){
                img_day.setImageDrawable(getResources().getDrawable(R.drawable.zhong_yu));
            }



            if(forecast.condNight.equals("晴")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.sun));
            }
            else if(forecast.condNight.equals("多云")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.duo_yun));
            }
            else if(forecast.condNight.equals("阴")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
            }
            else if(forecast.condNight.equals("小雨")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.light_rain));
            }
            else if(forecast.condNight.equals("中雨")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.zhong_yu));
            }
            else if(forecast.condNight.equals("大雨")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.da_yu));
            }
            else if(forecast.condNight.equals("暴雨")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.bao_yu));
            }
            else if(forecast.condDay.equals("阵雨")){
                img_night.setImageDrawable(getResources().getDrawable(R.drawable.zhong_yu));
            }

//            Log.d(TAG,"白天天气：" + forecast.condDay);
//            Log.d(TAG,"夜晚天气：" + forecast.condNight);

        }

        //获取有关生活建议方面的信息，例如舒适度指数等
        lifestyleLayout.removeAllViews();
        for(Lifestyle lifestyle : weather.lifestyleList){
            View view = LayoutInflater.from(this).inflate(R.layout.lifestyle_item,lifestyleLayout,false);
            TextView type = (TextView)view.findViewById(R.id.type);
            TextView brf = (TextView)view.findViewById(R.id.brf);
            TextView txt = (TextView)view.findViewById(R.id.txt);
            type.setText(lifestyle.type);
            brf.setText(lifestyle.brf);
            txt.setText(lifestyle.txt);
            lifestyleLayout.addView(view);
        }

        weatherLayout.setVisibility(View.VISIBLE);
    }

    public Timer timer = new Timer();

    //实现自动更新数据的TimerTask方法
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WeatherActivity.this,"(自动)数据刷新成功",
                            Toast.LENGTH_SHORT).show();
                    requestWeather(mWeatherId);
                    initView();
                }
            });
        }
    };




    //初始化控件
    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);

        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);

        lifestyleLayout = (LinearLayout)findViewById(R.id.lifestyle_layout);

        aqiText = (TextView)findViewById(R.id.aqi_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
    }
}
