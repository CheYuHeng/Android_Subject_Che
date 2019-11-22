package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import bean.Music;

public class MainActivity extends AppCompatActivity{

    ListView myMusic;
    List<Music> lists;
    MediaPlayer mediaPlayer;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();

    }


    public void click(View view) {

        switch (view.getId()){
            case R.id.button_start:
                mediaPlayer.start();
                break;

            case R.id.button_pause:
                mediaPlayer.pause();
                break;

            case R.id.button_last:
                index--;
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(lists.get(index).getData());

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_next:
                index++;
                mediaPlayer.reset();
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

}
