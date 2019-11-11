package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordOpenHelper extends SQLiteOpenHelper {
    public RecordOpenHelper(Context context){
        super(context, "redate", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table myrecord(" +                  //表名设置为myrecord
                "num integer PRIMARY KEY autoincrement," +   //设置id自增
                "title text," +                              //设置标题为文本类型
                "content text," +                            //设置内容为文本类型
                "times text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
