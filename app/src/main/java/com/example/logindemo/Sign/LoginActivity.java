package com.example.logindemo.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.logindemo.R;
import com.example.logindemo.activity.MainActivity;

import cn.bmob.v3.Bmob;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText accountText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "2991adef82274d2ea5fc1ff67d8b4842");//初始化Bmob
        setContentView(R.layout.activity_login);

        accountText = (EditText) findViewById(R.id.input_account);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signText = (TextView) findViewById(R.id.link_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//                BmobUser b = new BmobUser();
//                String account = accountText.getText().toString();
//                String password = passwordText.getText().toString();
//                b.setUsername(account);
//                b.setPassword(password);
//                b.login(new SaveListener<BmobUser>() {
//
//                    @Override
//                    public void done(BmobUser bmobUser, BmobException e) {
//                        if(e==null){
//                            Toast.makeText(LoginActivity.this,"登录成功:", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
//                        }else{
//                        }
//                    }
//                });

            }
        });

        signText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }
}
