package bean;

import android.net.Uri;
import android.provider.BaseColumns;

public class Record {
    private String title;   //标题
    private String content; //内容
    private String times;   //时间
    private int num;

    public static final String AUTHORITY = "com.example.memo.provider";

    public Record(){

    }

    public static abstract class Rec implements BaseColumns{
        public static final String TABLE_NAME = "myrecord";

        public static final String COLUMN_NAME_NUM = "num";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_TIMES = "times";

        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
        public static final String MIME_ITEM = "vnd.com.example.memo.provider.myrecord";

        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MIME_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MIME_ITEM;

        public static final String PATH_SINGLE = "myrecord/#";
        public static final String PATH_MULTIPLE = "myrecord";

        public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    }

    public Record(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Record(int num, String title, String times){
        this.num = num;
        this.title = title;
        this.times = times;
    }

    public Record(String title, String content, String times){
        this.title = title;
        this.content = content;
        this.times = times;
    }

    public Record(int num, String title, String content, String times){
        this.num = num;
        this.title = title;
        this.content = content;
        this.times = times;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setTimes(String times){
        this.times = times;
    }

    public String getTimes(){
        return times;
    }

    public void setNum(int num){
        this.num = num;
    }

    public int getNum(){
        return num;
    }


}
