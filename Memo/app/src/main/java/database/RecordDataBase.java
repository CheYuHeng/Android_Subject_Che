package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bean.Record;

public class RecordDataBase {
    Context context;
    RecordOpenHelper rdOpenHelper;
    SQLiteDatabase rddatabase;

    public RecordDataBase(Context context){
        this.context = context;
        rdOpenHelper = new RecordOpenHelper(context);
    }

    public ArrayList<Record> getArray(){
        ArrayList<Record> array = new ArrayList<Record>();
        ArrayList<Record> array_1 = new ArrayList<Record>();

        rddatabase = rdOpenHelper.getWritableDatabase();

        Cursor cursor = rddatabase.rawQuery("select num,title,times from myrecord",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("ids"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String times = cursor.getString(cursor.getColumnIndex("times"));
            Record record = new Record(id, title, times);
            array.add(record);
            cursor.moveToNext();
        }
        rddatabase.close();
        for (int i = array.size(); i > 0; i--) {
            array_1.add(array.get(i-1));
        }
        return array_1;
    }

    public Record getEditRecord(int num){
        rddatabase = rdOpenHelper.getWritableDatabase();
        Cursor cursor = rddatabase.rawQuery("select title,content from myrecord where num='"+ num +"'" , null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex("title"));
        String content=cursor.getString(cursor.getColumnIndex("content"));
        Record record = new Record(title,content);
        rddatabase.close();
        return record;
    }

    public void toUpdate(Record record){           //修改表中数据
        rddatabase = rdOpenHelper.getWritableDatabase();
        rddatabase.execSQL(
                "update myrecord set title = '" + record.getTitle()+
                        "',times = '" + record.getTimes() +
                        "',content = '" + record.getContent() +
                        "' where ids = '" + record.getNum()+"'");
        rddatabase.close();
    }

    public void toInsert(Record record){           //在表中插入新建的便签的数据
        rddatabase = rdOpenHelper.getWritableDatabase();
        rddatabase.execSQL("insert into myrecord(title,content,times)values('"
                + record.getTitle()+"','"
                + record.getContent()+"','"
                + record.getTimes()
                + "')");
        rddatabase.close();
    }

    public void toDelete(int num){            //在表中删除数据
        rddatabase  = rdOpenHelper.getWritableDatabase();
        rddatabase.execSQL("delete from myrecord where num =" + num +"");
        rddatabase.close();
    }
}
