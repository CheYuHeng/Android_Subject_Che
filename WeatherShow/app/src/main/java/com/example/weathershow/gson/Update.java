package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

public class Update {
    //当地时间（24小时制）
    @SerializedName("loc")
    public String updateTime;
}
