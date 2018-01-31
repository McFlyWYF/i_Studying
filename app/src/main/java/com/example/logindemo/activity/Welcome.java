package com.example.logindemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.logindemo.R;
import com.example.logindemo.Sign.LoginSystem;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 王宇飞 on 2018/1/30/030.
 */

public class Welcome extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        //通过一个时间控制函数Timer，在实现一个活动与另一个活动的跳转。
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Welcome.this,LoginSystem.class));
                finish();

            }
        }, 3000);//这里停留时间为1000=1s。
    }

}
