package com.example.logindemo.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.logindemo.R;

public class LoginSystem extends AppCompatActivity {

    private Button student;
    private Button school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_system);

        student = (Button)findViewById(R.id.student_login);
        school = (Button)findViewById(R.id.school_login);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSystem.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
