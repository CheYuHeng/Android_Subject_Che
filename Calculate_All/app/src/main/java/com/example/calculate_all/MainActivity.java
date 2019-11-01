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
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText result;
    private TextView textview;
    boolean formular_begin = true;
    boolean num_begin = true;
    boolean can_input_dot = true;
    int kuo_num = 0;
    int[][] value_array =
            {

                    {1,1,0,1,1,0,1},
                    {1,1,1,0,0,1,1},
                    {1,0,0,0,0,0,1},
                    {1,0,0,1,1,0,1},
                    {0,0,0,0,1,0,1},
                    {1,0,0,1,1,0,1},
                    {0,0,1,0,0,1,1},
                    {1,1,0,1,1,0,1}
            };

    int row = 0,column;

    public String tmp_num = "";

    Stack<StringBuffer> out=new Stack<>();
    Stack<Character> chars=new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Button dot = (Button) findViewById(R.id.dot);

        Button clear = (Button) findViewById(R.id.clear);
        Button equals = (Button) findViewById(R.id.equals);
        Button left = (Button) findViewById(R.id.left);
        Button right = (Button) findViewById(R.id.right);

        Button add = (Button) findViewById(R.id.add);
        Button sub = (Button) findViewById(R.id.sub);
        Button mul = (Button) findViewById(R.id.mul);
        Button div = (Button) findViewById(R.id.div);

        Button sin = (Button) findViewById(R.id.sin);
        Button cos = (Button) findViewById(R.id.cos);
        Button tan = (Button) findViewById(R.id.tan);

        result = (EditText) findViewById(R.id.result);

        Button Jinzhi = (Button) findViewById(R.id.JinZhi);
        Button Length = (Button) findViewById(R.id.Length);
        Button Volume = (Button) findViewById(R.id.Volume);

        zero.setOnClickListener(this);                   //主程序回调
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        dot.setOnClickListener(this);

        clear.setOnClickListener(this);
        equals.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);

        sin.setOnClickListener(this);
        cos.setOnClickListener(this);
        tan.setOnClickListener(this);

        Jinzhi.setOnClickListener(to_Jinzhi);
        Length.setOnClickListener(to_Length);
        Volume.setOnClickListener(to_Volume);
    }

    Button.OnClickListener to_Jinzhi = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,JinZhiActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };

    Button.OnClickListener to_Length = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,LengthActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };

    Button.OnClickListener to_Volume = new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,VolumeActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v){                //事件的处理函数

        String str = result.getText().toString();
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
                column = 0;
                break;
            case R.id.dot:
                column = 1;
                break;
            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div:
                column = 2;
                break;
            case R.id.left:
                column = 4;
                break;
            case R.id.right:
                column = 5;
                break;

            case R.id.clear:
                str = "";
                result.setText("");
                clear_flag();
                row = 0;
                column = 6;
                break;
        }

        if(check_value(row,column)){

            switch(v.getId()){                               //v.getId() = R.id.*;
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
                case R.id.dot:
                    String key_char = getNum(tmp_num, ((Button)v).getText().toString());
                    tmp_num = tmp_num + key_char ;
                    result.setText(str + key_char);
                    break;
                case R.id.add:
                case R.id.sub:
                case R.id.mul:
                case R.id.div:
                    clear_flag();
                    result.setText(str + ((Button) v).getText());
                    break;
                case R.id.left:
                    clear_flag();
                    kuo_num += 1;
                    result.setText(str + ((Button) v).getText());
                    break;
                case R.id.right:
                    if(kuo_num > 0){
                        result.setText(str + ((Button) v).getText());
                        clear_flag();
                        kuo_num -= 1;
                        break;
                    }
                    else{
                        break;
                    }
                case R.id.clear:
                    clear_flag();
                    break;
                case R.id.sin:
                    double a = Math.toRadians(Double.parseDouble(result.getText().toString()));
                    result.setText(String.valueOf(Math.sin(a)));
                    break;
                case R.id.cos:
                    double b = Math.toRadians(Double.parseDouble(result.getText().toString()));
                    result.setText(String.valueOf(Math.cos(b)));
                    break;
                case R.id.tan:
                    double c = Math.toRadians(Double.parseDouble(result.getText().toString()));
                    result.setText(String.valueOf(Math.tan(c)));
                    break;
                case R.id.equals:
                    result.setText(start(result.getText().toString()));
                    break;
                default:
                    break;
            }
            row = column + 1;
        }
    }

    private void clear_flag(){
        formular_begin = true;
        num_begin = true;
        can_input_dot = true;
        tmp_num = "";
    }

    private boolean check_value(int row, int column){
        if(value_array[row][column] == 0){
            return false;
        }
        else{
            return true;
        }
    }

    private String getNum(String num1, String key){
        String tmp = "";
        switch(key){
            case "0":
                if(num_begin){
                    if(num1.equals("")){
                        tmp = "0";
                        break;
                    }
                    else{
                        break;
                    }
                }
                else{
                    tmp = key;
                    break;
                }
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                num_begin = false;
                tmp = key;
                break;
            case ".":
                num_begin = false;
                if(can_input_dot){
                    can_input_dot = false;
                    if(num1.equals("")){
                        tmp = "0.";
                    }
                    else{
                        tmp = key;
                    }
                    break;
                }
                else{
                    break;
                }
        }
        return(tmp);
    }

    public String start(String cource){
        chars.push('#');
        for(int i = 0; i < cource.length();) {
            if(isChar(cource.charAt(i))){     //如果这是运算符
                doChar(cource.charAt(i));
                i ++;
            }else{                            //如果这是数字
                    StringBuffer temp = new StringBuffer();
                    while(i < cource.length() && !isChar(cource.charAt(i))){    //对数字进行拼接
                        temp.append(cource.charAt(i));
                        i ++;
                    }
                doNum(temp);
            }
        }
        outStack();
        return out.peek().toString();
    }

    //具体的运算处理
    public  boolean isChar(char i){//判断这是不是运算符
        if(i >= '0' && i <= '9' || i == '.'){
            return false;
        }
        return true;
    }

    public void doChar(char operation){    //遇到运算符时的处理
        if(chars.peek() == '('){
            chars.push(operation);
            return;
        }
        if(operation == ')'){              //遇到了')'把'('之后的所有元素出栈
            while(!chars.isEmpty() && chars.peek() != '('){
                doCalculation(chars.pop());
            }
            if(!chars.isEmpty()){
                chars.pop();
            }
            return;
        }
        if(operationLv(operation) > operationLv(chars.peek())){
            chars.push(operation);
        }else{
            doCalculation(chars.pop());
            chars.push(operation);
        }
    }

    public void doNum(StringBuffer num){//遇到数字时的处理
        out.push(num);
    }

    public void outStack(){                           //在表达式的最后，所有元素都要出栈
        while(!chars.isEmpty()&&chars.peek() != '#'){ //每出一个运算符都要进行一次运算
            doCalculation(chars.pop());
        }
    }

    public void doCalculation(char chars){            //将两个数值取出进行运算，并将结果重新放入到栈中
        double b = Double.parseDouble(out.pop().toString());
        double a = Double.parseDouble(out.pop().toString());
        double c = 0;
        switch (chars){
            case '+':
                c = a + b;
                out.push(new StringBuffer(Double.toString(c)));
                break;
            case '-':
                c = a - b;
                out.push(new StringBuffer(Double.toString(c)));
                break;
            case '*':
                c = a * b;
                out.push(new StringBuffer(Double.toString(c)));
                break;
            case '/':
                c = a / b;
                out.push(new StringBuffer(Double.toString(c)));
                break;
        }
    }

    public int operationLv(char operation){//设定运算符的优先级
        switch (operation){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '(':
            case ')':
                return 3;
            default:
                return 0;
        }
    }
}
