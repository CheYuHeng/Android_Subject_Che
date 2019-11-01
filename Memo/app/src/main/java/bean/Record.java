package bean;

public class Record {
    private String title;   //标题
    private String content; //内容
    private String times;   //时间
    private int num;

    public Record(){

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
