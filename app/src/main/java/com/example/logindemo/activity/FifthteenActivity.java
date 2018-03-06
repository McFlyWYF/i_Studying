package com.example.logindemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logindemo.R;


public class FifthteenActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom_fitteen);

        Button one = (Button)findViewById(R.id.one_class);
        Button two = (Button)findViewById(R.id.two_class);

        imageView = (ImageView)findViewById(R.id.imageview_class) ;
        textView = (TextView)findViewById(R.id.textview_class);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.classroom);
                textView.setText(R.string.classroom1);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.classroom2);
                textView.setText(R.string.classroom2);
            }
        });
    }

}
