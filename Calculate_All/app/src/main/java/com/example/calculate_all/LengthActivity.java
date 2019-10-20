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

public class LengthActivity extends AppCompatActivity implements View.OnClickListener{

    EditText meter;
    EditText decimeter;
    EditText centimeter;

    String str_m;
    String str_dm;
    String str_cm;

    RadioButton b_meter;
    RadioButton b_decimeter;
    RadioButton b_centimeter;

    boolean can_input_dot = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

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

        Button clear = (Button) findViewById(R.id.clear);
        Button dot = (Button) findViewById(R.id.dot);
        Button equals = (Button) findViewById(R.id.equals);

        meter = (EditText) findViewById(R.id.meter);
        decimeter = (EditText) findViewById(R.id.decimeter);
        centimeter = (EditText) findViewById(R.id.centimeter);

        b_meter = (RadioButton) findViewById(R.id.b_meter);
        b_decimeter = (RadioButton) findViewById(R.id.b_decimeter);
        b_centimeter = (RadioButton) findViewById(R.id.b_centimeter);

        Button Jinzhi = (Button) findViewById(R.id.JinZhi);
        Button Main = (Button) findViewById(R.id.Main);
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

        clear.setOnClickListener(this);
        dot.setOnClickListener(this);
        equals.setOnClickListener(this);

        b_meter.setOnClickListener(this);
        b_decimeter.setOnClickListener(this);
        b_centimeter.setOnClickListener(this);

        Jinzhi.setOnClickListener(to_JinZhi);
        Main.setOnClickListener(to_Main);
        Volume.setOnClickListener(to_Volume);
    }

    Button.OnClickListener to_JinZhi = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(LengthActivity.this,JinZhiActivity.class);
            startActivity(intent);
            LengthActivity.this.finish();
        }
    };

    Button.OnClickListener to_Main = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(LengthActivity.this,MainActivity.class);
            startActivity(intent);
            LengthActivity.this.finish();
        }
    };

    Button.OnClickListener to_Volume = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(LengthActivity.this,VolumeActivity.class);
            startActivity(intent);
            LengthActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v){

        str_m = meter.getText().toString();
        str_dm = decimeter.getText().toString();
        str_cm = centimeter.getText().toString();

        if(b_meter.isChecked()){
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
                    meter.setText(str_m + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_m.equals("")){
                            meter.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        meter.setText(str_m + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_m = "";
                    meter.setText("");
                    decimeter.setText("");
                    centimeter.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_m.equals("")){
                        meter.setText("0");
                        decimeter.setText("0");
                        centimeter.setText("0");
                    }
                    decimeter.setText(String.valueOf(Double.parseDouble(meter.getText().toString()) * 10));
                    centimeter.setText(String.valueOf(Double.parseDouble(meter.getText().toString()) * 100));
                    break;
            }
        }

        if(b_decimeter.isChecked()){
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
                    decimeter.setText(str_dm + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_dm.equals("")){
                            decimeter.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        decimeter.setText(str_dm + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_dm = "";
                    meter.setText("");
                    decimeter.setText("");
                    centimeter.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_dm.equals("")){
                        meter.setText("0");
                        decimeter.setText("0");
                        centimeter.setText("0");
                    }
                    meter.setText(String.valueOf(Double.parseDouble(decimeter.getText().toString()) / 10));
                    centimeter.setText(String.valueOf(Double.parseDouble(decimeter.getText().toString()) * 10));
                    break;
            }
        }

        if(b_centimeter.isChecked()){
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
                    centimeter.setText(str_cm + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_cm.equals("")){
                            centimeter.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        centimeter.setText(str_cm + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_cm = "";
                    meter.setText("");
                    decimeter.setText("");
                    centimeter.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_cm.equals("")){
                        meter.setText("0");
                        decimeter.setText("0");
                        centimeter.setText("0");
                    }
                    meter.setText(String.valueOf(Double.parseDouble(centimeter.getText().toString()) / 100));
                    decimeter.setText(String.valueOf(Double.parseDouble(centimeter.getText().toString()) / 10));
                    break;
            }
        }
    }
}
