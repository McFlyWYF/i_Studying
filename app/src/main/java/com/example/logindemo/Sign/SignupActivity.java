package com.example.logindemo.Sign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.logindemo.R;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText accountText;
    private EditText passwordText;
    private Button signButton;
    private TextView loginText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        accountText = (EditText)findViewById(R.id.input_name);
        passwordText = (EditText)findViewById(R.id.input_password);
        signButton = (Button)findViewById(R.id.btn_signup);
        loginText = (TextView) findViewById(R.id.link_login);

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = accountText.getText().toString();
                String password = passwordText.getText().toString();

                if (name.isEmpty() || name.length() < 5){
                    accountText.setError("用户名不能少于3个字符");
                }else{
                    accountText.setError(null);
                }

                if (password.isEmpty() || password.length() < 4 || password.length() >10){
                    passwordText.setError("密码在4-10个字符之间");
                }else{
                    passwordText.setError(null);
                }

                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name",name);
                editor.putString("password",password);
                editor.apply();

                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}