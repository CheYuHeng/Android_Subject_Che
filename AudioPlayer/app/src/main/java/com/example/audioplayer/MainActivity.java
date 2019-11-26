package com.example.audioplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.MusicAdapter;
import Util.MusicUtil;
import bean.Music;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private List<Music> lists;
    private MusicAdapter adapter;
    private ListView listView;
    private MediaPlayer mediaPlayer;
    private MediaPlayer next;
    private SeekBar seek;
    private Timer timer = new Timer();
    private int index = 0;
    private TextView progressTime;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    public int total_length;

    public Random ran;

    RadioButton single_cycle;
    RadioButton sequence;
    RadioButton random;

    TextView musicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seek = findViewById(R.id.seek);
        listView = findViewById(R.id.listView);

        single_cycle = findViewById(R.id.single_cycle);
        sequence = findViewById(R.id.sequence);
        random = findViewById(R.id.random);

        progressTime = (TextView) findViewById(R.id.time);
        seek.setProgress(0);
        mediaPlayer = new MediaPlayer();

        musicTitle = findViewById(R.id.music_title);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
        },1);
//        }



        //时间轴相关的属性设置
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //获得用户操作的通知
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b == true){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            //进度条开始被拖动时调用
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //进度条停止拖动时调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lists = new MusicUtil().getMusic(this);
        Log.i(TAG, "lists：" + lists);
        Log.i(TAG, "lists的长度：" + lists.size());
        total_length = lists.size();
        adapter = new MusicAdapter(lists, this);
        listView.setAdapter(adapter);

        //点击列表中的歌曲，开始播放对应的歌曲，并显示歌曲名
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Log.d(TAG,"index的长度：" + index);
                seek.setProgress(0);
                mediaPlayer.reset();
                musicTitle.setText(lists.get(i).getTitle());
                try {
                    seek.setMax(Integer.parseInt(lists.get(i).getDuration()));
                    mediaPlayer.setDataSource(lists.get(i).getData());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }).start();
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    progressTime.setText(time.format(mediaPlayer.getCurrentPosition()));
                    seek.setMax(mediaPlayer.getDuration());
                    seek.setProgress(mediaPlayer.getCurrentPosition());
                    break;
                default:
                    break;
            }
        }
    };


    //按钮点击事件处理
    public void click(View view) {

        //单曲循环
        if(single_cycle.isChecked()){
            mediaPlayer.setLooping(true);
        }

        //顺序播放
        if(sequence.isChecked()){
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    if(index == total_length - 1){
                        index = 0;
                        mediaPlayer.reset();
                        musicTitle.setText(lists.get(index).getTitle());
                        try {
                            mediaPlayer.setDataSource(lists.get(index).getData());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                            index++;
                            mediaPlayer.reset();
                            musicTitle.setText(lists.get(index).getTitle());
                            try {
                                mediaPlayer.setDataSource(lists.get(index).getData());
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                        }
                    }
                }
            });
        }

        //随机播放
        if(random.isChecked()){
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    mediaPlayer.reset();
                    ran = new Random();
                    int r = ran.nextInt(total_length);
                    Log.d(TAG,"r的值:" + r);
                    index = r;
                    mediaPlayer.reset();
                    musicTitle.setText(lists.get(index).getTitle());
                    try {
                        mediaPlayer.setDataSource(lists.get(index).getData());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        switch (view.getId()){

            //点击播放按钮
            case R.id.button_start:
                mediaPlayer.start();
                break;

            //点击暂停按钮
            case R.id.button_pause:
                mediaPlayer.pause();
                break;

            //播放上一首歌曲
            case R.id.button_last:

                if(index == 0){
                    index = total_length - 1;
                    mediaPlayer.reset();
                    musicTitle.setText(lists.get(index).getTitle());
                    try {
                        mediaPlayer.setDataSource(lists.get(index).getData());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                index --;
                Log.d(TAG,"index的长度（上）：" + index);
                Log.d(TAG,"total_length: " + total_length);
                mediaPlayer.reset();
                musicTitle.setText(lists.get(index).getTitle());
                try {
                    mediaPlayer.setDataSource(lists.get(index).getData());

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            //播放下一首歌曲
            case R.id.button_next:

                if(index == total_length - 1){
                    index = 0;
                    mediaPlayer.reset();
                    musicTitle.setText(lists.get(index).getTitle());
                    try {
                        mediaPlayer.setDataSource(lists.get(index).getData());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            index ++;
            Log.d(TAG,"index的长度（下）：" + index);
            mediaPlayer.reset();
            musicTitle.setText(lists.get(index).getTitle());
            try {
                mediaPlayer.setDataSource(lists.get(index).getData());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }

    }

    //释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
