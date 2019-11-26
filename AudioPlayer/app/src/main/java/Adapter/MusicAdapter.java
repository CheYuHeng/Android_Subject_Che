package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.audioplayer.R;

import java.util.List;

import Util.MusicUtil;
import bean.Music;

public class MusicAdapter extends BaseAdapter {
    private List<Music> lists;
    private Context context;
    public Music music;

    public MusicAdapter(List<Music> list, Context context) {
        this.lists = list;
        this.context = context;
    }

    //返回要显示的item的数量
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_music,null);
            holder.text_artist = view.findViewById(R.id.text_artist);
            holder.text_duration = view.findViewById(R.id.text_duration);
            holder.text_title = view.findViewById(R.id.text_title);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text_title.setText(lists.get(i).getTitle());
        holder.text_duration.setText(MusicUtil.formatTime(Long.parseLong(lists.get(i).getDuration())));
        holder.text_artist.setText(lists.get(i).getArtist());
        return view;
    }

    private class ViewHolder{
        private TextView text_title,text_artist,text_duration;
    }
}
