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

public class VolumeActivity extends AppCompatActivity implements View.OnClickListener{

    EditText meter3;
    EditText decimeter3;
    EditText centimeter3;

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
        setContentView(R.layout.activity_volume);

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

        meter3 = (EditText) findViewById(R.id.meter3);
        decimeter3 = (EditText) findViewById(R.id.decimeter3);
        centimeter3 = (EditText) findViewById(R.id.centimeter3);

        b_meter = (RadioButton) findViewById(R.id.b_meter);
        b_decimeter = (RadioButton) findViewById(R.id.b_decimeter);
        b_centimeter = (RadioButton) findViewById(R.id.b_centimeter);

        Button Jinzhi = (Button) findViewById(R.id.JinZhi);
        Button Length = (Button) findViewById(R.id.Length);
        Button Main = (Button) findViewById(R.id.Main);
        Button Help = (Button) findViewById(R.id.Help);

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

        Jinzhi.setOnClickListener(to_Jinzhi);
        Length.setOnClickListener(to_Length);
        Main.setOnClickListener(to_Main);
        Help.setOnClickListener(to_Help);
    }

    Button.OnClickListener to_Jinzhi = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(VolumeActivity.this,JinZhiActivity.class);
            startActivity(intent);
            VolumeActivity.this.finish();
        }
    };

    Button.OnClickListener to_Length = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(VolumeActivity.this,LengthActivity.class);
            startActivity(intent);
            VolumeActivity.this.finish();
        }
    };

    Button.OnClickListener to_Main = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(VolumeActivity.this,MainActivity.class);
            startActivity(intent);
            VolumeActivity.this.finish();
        }
    };

    Button.OnClickListener to_Help = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(VolumeActivity.this,HelpActivity.class);
            startActivity(intent);
            VolumeActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v){
        str_m = meter3.getText().toString();
        str_dm = decimeter3.getText().toString();
        str_cm = centimeter3.getText().toString();

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
                    meter3.setText(str_m + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_m.equals("")){
                            meter3.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        meter3.setText(str_m + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_m = "";
                    meter3.setText("");
                    decimeter3.setText("");
                    centimeter3.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_m.equals("")){
                        meter3.setText("0");
                        decimeter3.setText("0");
                        centimeter3.setText("0");
                    }
                    decimeter3.setText(String.valueOf(Double.parseDouble(meter3.getText().toString()) * 1000));
                    centimeter3.setText(String.valueOf(Double.parseDouble(meter3.getText().toString()) * 1000000));
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
                    decimeter3.setText(str_dm + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_dm.equals("")){
                            decimeter3.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        decimeter3.setText(str_dm + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_dm = "";
                    meter3.setText("");
                    decimeter3.setText("");
                    centimeter3.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_dm.equals("")){
                        meter3.setText("0");
                        decimeter3.setText("0");
                        centimeter3.setText("0");
                    }
                    meter3.setText(String.valueOf(Double.parseDouble(decimeter3.getText().toString()) / 1000));
                    centimeter3.setText(String.valueOf(Double.parseDouble(decimeter3.getText().toString()) * 1000));
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
                    centimeter3.setText(str_cm + ((Button) v).getText());
                    break;
                case R.id.dot:
                    if(can_input_dot){
                        if(str_cm.equals("")){
                            centimeter3.setText("0.");
                            can_input_dot = false;
                            break;
                        }
                        centimeter3.setText(str_cm + ((Button) v).getText());
                        can_input_dot = false;
                        break;
                    }
                    break;
                case R.id.clear:
                    str_cm = "";
                    meter3.setText("");
                    decimeter3.setText("");
                    centimeter3.setText("");
                    can_input_dot = true;
                    break;
                case R.id.equals:
                    if(str_cm.equals("")){
                        meter3.setText("0");
                        decimeter3.setText("0");
                        centimeter3.setText("0");
                    }
                    meter3.setText(String.valueOf(Double.parseDouble(centimeter3.getText().toString()) / 1000000));
                    decimeter3.setText(String.valueOf(Double.parseDouble(centimeter3.getText().toString()) / 1000));
                    break;
            }
        }

    }
}
