package com.example.xxsj.jingdongjd.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.xxsj.jingdongjd.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/6.
 */

public class GuidePageActivity extends Activity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                int time = (int) msg.obj;
                tvTime.setText(time+"秒");
               if (time==0){
                   Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        }
    };
    private TextView tvTime;
    private Timer timer;
    private TextView tvBreak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvBreak = (TextView) findViewById(R.id.tv_break);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int time=5;
            @Override
            public void run() {
                if (time!=0){
                    time--;
                }
                Message message = handler.obtainMessage();
                message.what=0;
                message.obj=time;
                handler.sendMessage(message);
            }
        },1000,1000);

        //设置跳过的监听事件
        tvBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            //关闭timer
            timer.cancel();
        }
    }
}
