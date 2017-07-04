package com.zucc.wl1145_mjy1136.personalassistant.user;

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
public class RegisterActivity extends AppCompatActivity {
    private DatabaseUserManager dbu;

    private EditText username;
    private EditText password;
    private EditText password2;
    private Button back;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText)findViewById(R.id.regi_name);
        password = (EditText)findViewById(R.id.regi_password);
        password2 = (EditText)findViewById(R.id.regi_confirm);
        back = (Button)findViewById(R.id.back_regi);
        register = (Button)findViewById(R.id.regi_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbu = new DatabaseUserManager(RegisterActivity.this);
                String info = dbu.insertRegi(username.getText().toString(),
                        password.getText().toString(),password2.getText().toString());
                Toast.makeText(RegisterActivity.this, info, Toast.LENGTH_LONG).show();
                if(info.equals("注册成功！"))
                    finish();
            }
        });
    }
}
