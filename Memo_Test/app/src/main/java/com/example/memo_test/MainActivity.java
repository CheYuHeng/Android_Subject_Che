package com.example.memo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData = (Button) findViewById(R.id.add_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);

        addData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Uri uri = Uri.parse("content://com.example.memo.provider/myrecord");

                ContentResolver contentResolver = getContentResolver();

                String title = "添加新的数据";
                String content = "From Memo_test!!";
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("content",content);

                Uri newUri = contentResolver.insert(uri, values);

                newId = newUri.getPathSegments().get(1);
                Log.d("MainActivity","成功添加一条新的数据！");
            }
        });

        queryData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("content://com.example.memo.provider/myrecord");

                Cursor cursor = getContentResolver().query(uri,null,
                        null,null,null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        String content = cursor.getString(cursor.getColumnIndex("content"));

                        Log.d("MainActivity","记录的标题: " + title);
                        Log.d("MainActivity","记录的正文: " + content);
                    }
                    cursor.close();
                }
            }
        });

        updateData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                newId = "3";
                String content = "After change from Memo_Test";

                Uri uri = Uri.parse("content://com.example.memo.provider/myrecord/" + newId);
                ContentValues values = new ContentValues();
//                values.put("content",content);
                values.put("title","000000");
                values.put("content","Second");
                getContentResolver().update(uri, values, null, null);

                Log.d("MainActivity","成功更新一条新的数据！");
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newId = "1";
                Uri uri = Uri.parse("content://com.example.memo.provider/myrecord/" + newId);
                getContentResolver().delete(uri,null,null);

                Log.d("MainActivity","成功删除一条新的数据！");
            }
        });
    }
}
