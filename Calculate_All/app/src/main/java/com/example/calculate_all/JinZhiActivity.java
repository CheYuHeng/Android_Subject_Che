package com.example.calculate_all;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class JinZhiActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText result_02;
    EditText result_08;
    EditText result_10;
    EditText result_16;

    String str_2;
    String str_8;
    String str_10;
    String str_16;

    RadioButton RB_02;
    RadioButton RB_08;
    RadioButton RB_10;
    RadioButton RB_16;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jin_zhi);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        Button zero = (Button) findViewById(R.id.zero);
        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        Button four = (Button) findViewById(R.id.four);
        Button five = (Button) findViewById(R.id.five);
        Button six = (Button) findViewById(R.id.six);
        Button seven = (Button) findViewById(R.id.seven);
        Button eight = (Button) findViewById(R.id.eight);
        Button nine = (Button) findViewById(R.id.nine);

        Button A = (Button) findViewById(R.id.A);
        Button B = (Button) findViewById(R.id.B);
        Button C = (Button) findViewById(R.id.C);
        Button D = (Button) findViewById(R.id.D);
        Button E = (Button) findViewById(R.id.E);
        Button F = (Button) findViewById(R.id.F);

        Button clear = (Button) findViewById(R.id.clear);
        Button equals = (Button) findViewById(R.id.equals);

        result_02 = (EditText) findViewById(R.id.two_system);
        result_08 = (EditText) findViewById(R.id.eight_system);
        result_10 = (EditText) findViewById(R.id.ten_system);
        result_16 = (EditText) findViewById(R.id.sixteen_system);

        RB_02 = (RadioButton) findViewById(R.id.jinzhi_02);
        RB_08 = (RadioButton) findViewById(R.id.jinzhi_08);
        RB_10 = (RadioButton) findViewById(R.id.jinzhi_10);
        RB_16 = (RadioButton) findViewById(R.id.jinzhi_16);

        Button Main = (Button) findViewById(R.id.Main);
        Button Length = (Button) findViewById(R.id.Length);
        Button Volume = (Button) findViewById(R.id.Volume);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

        A.setOnClickListener(this);
        B.setOnClickListener(this);
        C.setOnClickListener(this);
        D.setOnClickListener(this);
        E.setOnClickListener(this);
        F.setOnClickListener(this);

        clear.setOnClickListener(this);
        equals.setOnClickListener(this);

        RB_02.setOnClickListener(this);
        RB_08.setOnClickListener(this);
        RB_10.setOnClickListener(this);
        RB_16.setOnClickListener(this);

        Main.setOnClickListener(to_Main);
        Length.setOnClickListener(to_Length);
        Volume.setOnClickListener(to_Volume);
    }

    Button.OnClickListener to_Main = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(JinZhiActivity.this,MainActivity.class);
            startActivity(intent);
            JinZhiActivity.this.finish();
        }
    };

    Button.OnClickListener to_Length = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(JinZhiActivity.this,LengthActivity.class);
            startActivity(intent);
            JinZhiActivity.this.finish();
        }
    };

    Button.OnClickListener to_Volume = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(JinZhiActivity.this,VolumeActivity.class);
            startActivity(intent);
            JinZhiActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v){

        str_2 = result_02.getText().toString();
        str_8 = result_08.getText().toString();
        str_10 = result_10.getText().toString();
        str_16 = result_16.getText().toString();

        if(RB_02.isChecked()){
            switch(v.getId()){
                case R.id.zero:
                case R.id.one:
                    result_02.setText(str_2 + ((Button) v).getText());
                    break;
                case R.id.clear:
                    str_2 = "";
                    result_02.setText("");
                    result_08.setText("");
                    result_10.setText("");
                    result_16.setText("");
                    break;
                case R.id.equals:
                    result_08.setText(Integer.toOctalString(Integer.valueOf(Integer.valueOf(result_02.getText().toString(),2).toString()).intValue()));
                    result_10.setText(Integer.valueOf(result_02.getText().toString(),2).toString());
                    result_16.setText(Integer.toHexString(Integer.valueOf(Integer.valueOf(result_02.getText().toString(),2).toString()).intValue()));
                    break;
            }
        }

        if(RB_08.isChecked()){
            switch(v.getId()){
                case R.id.zero:
                case R.id.one:
                case R.id.two:
                case R.id.three:
                case R.id.four:
                case R.id.five:
                case R.id.six:
                case R.id.seven:
                    result_08.setText(str_8 + ((Button) v).getText());
                    break;
                case R.id.clear:
                    str_8 = "";
                    result_02.setText("");
                    result_08.setText("");
                    result_10.setText("");
                    result_16.setText("");
                    break;
                case R.id.equals:
                    result_02.setText(Integer.toBinaryString(Integer.valueOf(Integer.valueOf(result_08.getText().toString(),8).toString()).intValue()));
                    result_10.setText(Integer.valueOf(result_08.getText().toString(),8).toString());
                    result_16.setText(Integer.toHexString(Integer.valueOf(Integer.valueOf(result_08.getText().toString(),8).toString()).intValue()));
                    break;
            }
        }

        if(RB_10.isChecked()){
            switch(v.getId()) {
                case R.id.zero:
                case R.id.one:
                case R.id.two:
                case R.id.three:
                case R.id.four:
                case R.id.five:
                case R.id.six:
                case R.id.seven:
                case R.id.eight:
                case R.id.nine:
                    result_10.setText(str_10 + ((Button) v).getText());
                    break;
                case R.id.clear:
                    str_10 = "";
                    result_02.setText("");
                    result_08.setText("");
                    result_10.setText("");
                    result_16.setText("");
                    break;
                case R.id.equals:
                    result_02.setText(Integer.toBinaryString(Integer.valueOf(result_10.getText().toString()).intValue()));
                    result_08.setText(Integer.toOctalString(Integer.valueOf(result_10.getText().toString()).intValue()));
                    result_16.setText(Integer.toHexString(Integer.valueOf(result_10.getText().toString()).intValue()));
                    break;
            }
        }

        if(RB_16.isChecked()){
            switch(v.getId()){
                case R.id.zero:
                case R.id.one:
                case R.id.two:
                case R.id.three:
                case R.id.four:
                case R.id.five:
                case R.id.six:
                case R.id.seven:
                case R.id.eight:
                case R.id.nine:
                case R.id.A:
                case R.id.B:
                case R.id.C:
                case R.id.D:
                case R.id.E:
                case R.id.F:
                    result_16.setText(str_16 + ((Button) v).getText());
                    break;
                case R.id.clear:
                    str_16 = "";
                    result_02.setText("");
                    result_08.setText("");
                    result_10.setText("");
                    result_16.setText("");
                    break;
                case R.id.equals:
                    result_02.setText(Integer.toBinaryString(Integer.valueOf(Integer.valueOf(result_16.getText().toString(),16).toString()).intValue()));
                    result_08.setText(Integer.toOctalString(Integer.valueOf(Integer.valueOf(result_16.getText().toString(),16).toString()).intValue()));
                    result_10.setText(Integer.valueOf(result_16.getText().toString(),16).toString());
                    break;
            }
        }
    }
}
