package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    //实况天气描述
    @SerializedName("cond_txt")
    public String cond;

    //实况天气代码
    @SerializedName("cond_code")
    public String condCode;

    //温度
    @SerializedName("tmp")
    public String tmp;

    //降水量
    @SerializedName("pcpn")
    public String pcpn;

    //相对湿度
    @SerializedName("hum")
    public String hum;

    //能见度
    @SerializedName("vis")
    public String vis;
}
