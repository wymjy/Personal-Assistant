package com.zucc.wl1145_mjy1136.personalassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;
import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseMainActivity;
import com.zucc.wl1145_mjy1136.personalassistant.user.DatabaseUserManager;
import com.zucc.wl1145_mjy1136.personalassistant.user.LoginActivity;
import com.zucc.wl1145_mjy1136.personalassistant.user.UserMainActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton userButton;
    private Button openButton;
    private Button closeButton;
    private SlidingMenu mSlidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlidingMenu = new SlidingMenu(this, LayoutInflater
                .from(this).inflate(R.layout.activity_main, null), LayoutInflater
                .from(this).inflate(R.layout.left_fragment, null));
        setContentView(mSlidingMenu);//注意setContentView需要换为我们的SlidingMenu
        userButton = (ImageButton) findViewById(R.id.head);
        openButton = (Button) findViewById(R.id.button_more_main);
        closeButton = (Button) findViewById(R.id.button_close);
        //用户头像
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DatabaseUserManager.currentUser.equals("")){
                    Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSlidingMenu.open();
                Toast.makeText(MainActivity.this, DatabaseUserManager.currentUser, Toast.LENGTH_SHORT).show();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSlidingMenu.close();
            }
        });

         //侧栏id
        Button buttonCalendar = (Button)findViewById(R.id.item3_ce);
        Button buttonExpense = (Button)findViewById(R.id.item4_ce);
        Button buttonQuit = (Button)findViewById(R.id.item6_ce);

        //退出程序
        buttonQuit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity.this.finish();
            }
        });

         //今日日程
        buttonCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CalendarTodayActivity.class);
                startActivity(intent);
            }
        });

        //收支管理
        buttonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExpenseMainActivity.class);
                startActivity(intent);
            }
        });

    }
}
