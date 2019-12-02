package com.example.weathershow.gson;

import com.google.gson.annotations.SerializedName;

public class Lifestyle {
    //生活指数类型
    public String type;

    //生活指数简介
    public String brf;

    //生活指数详细描述
    @SerializedName("txt")
    public String txt;

    //舒适度指数
    @SerializedName("comf")
    public String comf;

}
