package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import bean.Record;
import database.RecordAdapter;
import database.RecordDataBase;

public class MainActivity extends AppCompatActivity{

    ListView listView;
//    FloatingActionButton floatingActionButton;
    LayoutInflater layoutInflater;
    ArrayList<Record> arrayList;
    RecordDataBase rdDatabase;
    RecordAdapter adapter;

    Button newRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        listView = (ListView) findViewById(R.id.layout_listview);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_record);
        newRecord = (Button) findViewById(R.id.new_record);
        layoutInflater = getLayoutInflater();

        rdDatabase = new RecordDataBase(this);
        arrayList = rdDatabase.getArray();
        adapter = new RecordAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //点击一下跳转到编辑页面（编辑页面与新建页面共用一个布局）
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),New_Record.class);
                intent.putExtra("num",arrayList.get(position).getNum());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {   //长按删除
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("确定要删除本条记录？")
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rdDatabase.toDelete(arrayList.get(position).getNum());
                                adapter = new RecordAdapter(getApplicationContext(),arrayList);
                                listView.setAdapter(adapter);
                            }
                        })
                        .create()
                        .show();
                return true;
            }
        });


//        floatingActionButton.setOnClickListener(new View.OnClickListener() {   //点击悬浮按钮时，跳转到新建页面
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), New_Record.class);
//                startActivity(intent);
//                MainActivity.this.finish();
//            }
//        });

        newRecord.setOnClickListener(new View.OnClickListener() {   //点击悬浮按钮时，跳转到新建页面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), New_Record.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_operate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_new_record:                              //点击菜单中的新建按钮时，新建备忘录
                Intent intent = new Intent(getApplicationContext(),New_Record.class);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.menu_exit:
                MainActivity.this.finish();
                break;
            default:
                break;
        }
        return  true;
    }



}
