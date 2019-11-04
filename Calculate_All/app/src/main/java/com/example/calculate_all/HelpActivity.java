package com.example.calculate_all;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        Button main = (Button) findViewById(R.id.Main);
        Button jinzhi = (Button) findViewById(R.id.JinZhi);
        Button length = (Button) findViewById(R.id.Length);
        Button volume = (Button) findViewById(R.id.Volume);

        main.setOnClickListener(to_Main);
        jinzhi.setOnClickListener(to_Jinzhi);
        length.setOnClickListener(to_Length);
        volume.setOnClickListener(to_Volume);
    }



    Button.OnClickListener to_Main = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(HelpActivity.this,MainActivity.class);
            startActivity(intent);
            HelpActivity.this.finish();
        }
    };

    Button.OnClickListener to_Jinzhi = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(HelpActivity.this,JinZhiActivity.class);
            startActivity(intent);
            HelpActivity.this.finish();
        }
    };

    Button.OnClickListener to_Length = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(HelpActivity.this,LengthActivity.class);
            startActivity(intent);
            HelpActivity.this.finish();
        }
    };

    Button.OnClickListener to_Volume = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(HelpActivity.this,VolumeActivity.class);
            startActivity(intent);
            HelpActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v){

    }

}
