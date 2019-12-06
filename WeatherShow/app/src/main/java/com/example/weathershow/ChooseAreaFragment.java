package com.example.weathershow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathershow.db.City;
import com.example.weathershow.db.County;
import com.example.weathershow.db.Province;
import com.example.weathershow.util.HttpUtil;
import com.example.weathershow.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;


public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";

    public static final int LEVEL_PROVINCE = 0;       //第0级，省份

    public static final int LEVEL_CITY = 1;           //第1级，城市

    public static final int LEVEL_COUNTY = 2;         //第2级，县

    private TextView titleText;

    private Button backButton;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;  //省份列表

    private List<City> cityList;          //城市列表

    private List<County> countyList;      //县列表

    private Province selectedProvince;    //被选中的省份

    private City selectedCity;            //被选中的城市

    private int currentLevel;              //所选的级别


    //获取控件的实例然后初始化ArrayAdapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY){
                    String weatherId = countyList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(),WeatherActivity.class); //跳转到天气页面
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {    //点击返回按钮的处理
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    //查询全国所有省份、直辖市的数据
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    //查询被选中省份内所包含的所有城市
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();      //获取被选中的是哪个省份
            String address = "http://guolin.tech/api/china/" + provinceCode;    //字符串拼接
            queryFromServer(address, "city");                     //调用查询服务器的方法，同时告知这是城市这一级
        }
    }

    //查询被选中城市内所包含的所有县
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();  //所选省名
            int cityCode = selectedCity.getCityCode();              //所选市名
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    //从服务器上查询相应省、市、县的数据
    private void queryFromServer(String address, final String type) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {   //向服务器发送请求
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {      //省的处理
                    result = Utility.handleProvinceResponse(responseText);  //获取服务器中的信息并解析处理
                } else if ("city".equals(type)) {   //市的处理
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) { //县的处理
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }
                if (result) {                                   //result == true
                    getActivity().runOnUiThread(new Runnable() {        //从子线程切换到了主线程
                        @Override
                        public void run() {
                            if ("province".equals(type)) {         //查询省份
                                queryProvinces();
                            } else if ("city".equals(type)) {       //查询城市
                                queryCities();
                            } else if ("county".equals(type)) {     //查询县
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

                // 通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
