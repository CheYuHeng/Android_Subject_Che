package com.example.intent_connect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button second = (Button) findViewById(R.id.Second);
        Button third = (Button) findViewById(R.id.Third);

        second.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String ss = "这是一个字符串(来自MainActivity)";
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("information:",ss);
                startActivity(intent);
            }
        });

        third.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("return");
                    Log.d("MainActivity",returnData);
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v){

    }
}
