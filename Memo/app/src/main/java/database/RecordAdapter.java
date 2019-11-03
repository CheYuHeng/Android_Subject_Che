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


}
