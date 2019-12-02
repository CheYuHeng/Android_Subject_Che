package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    //城市名称
    @SerializedName("location")
    public String cityName;

    //城市ID
    @SerializedName("cid")
    public String weatherId;
}
