package com.example.memo;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import bean.Record;
import database.RecordDataBase;

public class New_Record extends AppCompatActivity {

    EditText ed_title;
    EditText ed_content;
//    FloatingActionButton floatingActionButton;
    Button saveButton;

    RecordDataBase rdDatabase;
    Record record;
    int num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        ed_title = (EditText)findViewById(R.id.title);
        ed_content = (EditText)findViewById(R.id.content);

        saveButton = (Button)findViewById(R.id.save);

//        floatingActionButton = (FloatingActionButton)findViewById(R.id.finish);
        rdDatabase = new RecordDataBase(this);
        Intent intent = this.getIntent();
        num = intent.getIntExtra("num",0);
        if (num != 0){
            record = rdDatabase.getEditRecord(num);
            ed_title.setText(record.getTitle());
            ed_content.setText(record.getContent());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });

//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSave();
//            }
//        });
    }

    @Override
    public void onBackPressed() {     //重写返回建方法，如果是属于新建则插入数据表并返回主页面，如果是修改，修改表中数据并返回主页面
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String title = ed_title.getText().toString();
        String content = ed_content.getText().toString();
        if(num!=0){
            record = new Record(num, title, content, time);
            rdDatabase.toUpdate(record);
            Intent intent=new Intent(New_Record.this,MainActivity.class);
            startActivity(intent);
            New_Record.this.finish();
        }
        //新建日记
        else{
            record = new Record(title,content,time);
            rdDatabase.toInsert(record);
            Intent intent=new Intent(New_Record.this,MainActivity.class);
            startActivity(intent);
            New_Record.this.finish();
        }
    }

    private void isSave(){   //写一个方法进行调用，如果是属于新建则插入数据表并返回主页面，如果是修改，修改表中数据并返回主页面
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH：mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        Log.d("new_note", "isSave: "+time);
        String title = ed_title.getText().toString();
        String content = ed_content.getText().toString();
        if(num!=0){
            record=new Record(num, title, content, time);
            rdDatabase.toUpdate(record);
            Intent intent=new Intent(New_Record.this,MainActivity.class);
            startActivity(intent);
            New_Record.this.finish();
        }
        //新建日记
        else{
            record = new Record(title,content,time);
            rdDatabase.toInsert(record);
            Intent intent=new Intent(New_Record.this,MainActivity.class);
            startActivity(intent);
            New_Record.this.finish();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.new_lo,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.new_share :
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT,
//                        "标题："+ed_title.getText().toString()+"    " +
//                                "内容："+ed_content.getText().toString());
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//        return false;
//    }

}
