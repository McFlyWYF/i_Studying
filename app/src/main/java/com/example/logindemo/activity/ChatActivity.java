package com.example.logindemo.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.logindemo.R;


public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_chat_fragment);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.string.chat);
    }
}
