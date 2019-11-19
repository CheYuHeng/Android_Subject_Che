package com.example.implict_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button change;

    /*  实践三，练习Intent的隐式启动
     *
     *  通过隐式intent，实现启动电话号码的拨号页面
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        change = findViewById(R.id.change);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);             //打开内置拨号界面并显示uri中提供的号码
                intent.setData(Uri.parse("tel:13301339527"));            //废弃号码
                startActivity(intent);
            }
        });
    }
}
