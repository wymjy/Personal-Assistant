package com.zucc.wl1145_mjy1136.personalassistant.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.expense.DatabaseExpenseManager;

/**
 * Created by wanglei on 2017/7/4.
 */
public class UserMainActivity extends AppCompatActivity{
    private TextView user_name;
    private Button quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        user_name = (TextView)findViewById(R.id.user_name);
        quit = (Button) findViewById(R.id.user_quit);
        user_name.setText(DatabaseUserManager.currentUser);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUserManager.currentUser="";
                finish();
            }
        });
    }
}
