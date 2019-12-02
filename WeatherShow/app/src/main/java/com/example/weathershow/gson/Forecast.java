package com.example.weathershow.gson;
import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("tmp")
    public Temperature temperature;
    //
//    @SerializedName("cond")
//    public More more;
//
    public class Temperature {
        public String max;
        public String min;
    }
    //
//    private class More {
//        @SerializedName("txt_d")
//        public String info;
//    }
    public String date;

    @SerializedName("wind_dir")
    public String windDir;


    //白天天气描述
    @SerializedName("cond_txt_d")
    public String condDay;

    //晚间天气描述
    @SerializedName("cond_txt_n")
    public String condNight;

    //白天天气
    @SerializedName("cond_code_d")
    public String condDayCode;

    //夜晚天气
    @SerializedName("cond_code_n")
    public String condNightCode;
}
