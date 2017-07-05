package com.zucc.wl1145_mjy1136.personalassistant.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zucc.wl1145_mjy1136.personalassistant.R;

/**
 * Created by wanglei on 2017/7/4.
 */
public class LoginActivity extends AppCompatActivity{
    private DatabaseUserHelper dbu;

    private EditText username;
    private EditText userpassword;
    private Button login;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.login_name);
        userpassword = (EditText)findViewById(R.id.login_password);
        login = (Button)findViewById(R.id.login_login);
        register = (Button)findViewById(R.id.login_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbu = new DatabaseUserHelper(LoginActivity.this);
                String info = dbu.checkLogin(username.getText().toString(), userpassword.getText().toString());
                //if()
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
