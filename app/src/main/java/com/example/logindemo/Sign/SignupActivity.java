package com.example.logindemo.Sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logindemo.R;
import com.example.logindemo.db.Person;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
                String account = accountText.getText().toString();
                String password = passwordText.getText().toString();
                Person p = new Person();
                p.setName(account);
                p.setPassword(password);
                p.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(SignupActivity.this,"添加数据成功，返回为"+s,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignupActivity.this,"创建数据失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                String name = accountText.getText().toString();
//                String password = passwordText.getText().toString();
//
//                if (name.isEmpty() || name.length() < 5){
//                    accountText.setError("用户名不能少于3个字符");
//                }else{
//                    accountText.setError(null);
//                }
//
//                if (password.isEmpty() || password.length() < 4 || password.length() >10){
//                    passwordText.setError("密码在4-10个字符之间");
//                }else{
//                    passwordText.setError(null);
//                }
//
//                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
//                editor.putString("name",name);
//                editor.putString("password",password);
//                editor.apply();
//
//                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
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