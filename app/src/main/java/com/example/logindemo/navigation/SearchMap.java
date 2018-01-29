package com.example.logindemo.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.logindemo.R;

public class SearchMap extends AppCompatActivity {

    private Button search_map_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        search_map_btn = (Button)findViewById(R.id.search_road_map);
        search_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchMap.this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
