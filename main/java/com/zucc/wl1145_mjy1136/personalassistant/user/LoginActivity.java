package com.zucc.wl1145_mjy1136.personalassistant.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zucc.wl1145_mjy1136.personalassistant.R;

/**
 * Created by wanglei on 2017/7/4.
 */
public class LoginActivity extends AppCompatActivity{
    private DatabaseUserManager dbu;

    private EditText username;
    private EditText userpassword;
    private Button back;
    private Button login;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.login_name);
        userpassword = (EditText)findViewById(R.id.login_password);
        back = (Button)findViewById(R.id.back_login);
        login = (Button)findViewById(R.id.login_login);
        register = (Button)findViewById(R.id.login_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbu = new DatabaseUserManager(LoginActivity.this);
                String info = dbu.checkLogin(username.getText().toString(), userpassword.getText().toString());
                if(!info.equals("yes"))
                    Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
                else{
                    DatabaseUserManager.currentUser = username.getText().toString();
                    finish();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
