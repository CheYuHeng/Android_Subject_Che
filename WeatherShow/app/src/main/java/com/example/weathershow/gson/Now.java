package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    //实况天气描述
    @SerializedName("cond_txt")
    public String cond;

    //实况天气代码
    @SerializedName("cond_code")
    public String condCode;
}
