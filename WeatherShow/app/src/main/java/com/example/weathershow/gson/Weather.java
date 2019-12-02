package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    public Forecast forecast;
    public String status;

    public Basic basic;

    public Now now;

    @SerializedName("lifestyle")
    public List<Lifestyle> lifestyleList;

    public Update update;

    //数组形式获取信息
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
