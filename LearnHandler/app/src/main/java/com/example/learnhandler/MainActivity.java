package com.example.learnhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.os.Message;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private HandlerTest1 handler1;
    private HandlerTest2 handler2;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        mTextView = (TextView) findViewById(R.id.textView);

        new Thread() {
            public void run() {
                Looper.prepare();                           //子线程处理消息
                handler1 = new HandlerTest1(Looper.myLooper());
                Message message = new Message();
                message.obj = "子线程发送的消息001";
                handler1.sendMessage(message);              //子线程发送消息
                Looper.loop();
            }
        }.start();

    }

    private class HandlerTest1 extends Handler {

        private HandlerTest1(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("子线程收到:" + msg.obj);

            handler2 = new HandlerTest2(getMainLooper());   //刷新UI
            Message message = new Message();
            message.obj = "你好(来自子线程)";
            handler2.sendMessage(message);                  //子线程发送信息
        }
    }



    private class HandlerTest2 extends Handler {

        private HandlerTest2(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTextView.setText("在主线程中,收到子线程发来消息:" + msg.obj);


            if (counter == 0) {
                Message message = new Message();
                message.obj = "这是主线程发送的消息";
                handler1.sendMessage(message);          //主线程发送消息
                counter += 1;
            }

        }
    }

}
