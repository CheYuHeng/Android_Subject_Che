package com.example.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.example.dialog.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button test = (Button) findViewById(R.id.button_test);

        Button login = (Button) findViewById(R.id.login);


        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("这是一个普通的对话框！").setTitle("这里是标题！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"用户按了确认按钮",Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"用户按了取消按钮",Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"用户按了忽略按钮",Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();

            }
        });

        //验证用户名和密码是否准确，匹配字符串
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View window = inflater.inflate(R.layout.login_dialog,null);


                builder.setView(window)
                        .setTitle("登录对话框").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText UserID = (EditText) window.findViewById(R.id.UserID);
                        EditText UserPWD = (EditText) window.findViewById(R.id.UserPWD);


                        String userName = UserID.getText().toString().trim();
                        String userPassWord = UserPWD.getText().toString().trim();

                        String trueName = "abc";
                        String truePWD = "123";

                        if(userName.equals(trueName.trim()) && userPassWord.equals(truePWD.trim())){
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(MainActivity.this, "用户取消了登录", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });

    }
}
