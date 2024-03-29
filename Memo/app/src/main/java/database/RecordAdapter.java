package database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memo.R;

import java.util.ArrayList;
import bean.Record;

public class RecordAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Record> arrlist;
    Context context;

    public RecordAdapter(Context context,ArrayList<Record> arrlist){
        this.context = context;
        this.arrlist = arrlist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrlist.size();
    }

    @Override
    public Object getItem(int position) {
        return arrlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.list_view,null);
            vh.tv1=(TextView) convertView.findViewById(R.id.list_title);
            vh.tv2=(TextView) convertView.findViewById(R.id.list_time);
            convertView.setTag(vh);
        }
        vh=(ViewHolder) convertView.getTag();
        vh.tv1.setText(arrlist.get(position).getTitle());
        vh.tv2.setText(arrlist.get(position).getTimes());
        return convertView;
    }

    class ViewHolder{     //内部类，对控件进行缓存
        TextView tv1,tv2;
    }
}
