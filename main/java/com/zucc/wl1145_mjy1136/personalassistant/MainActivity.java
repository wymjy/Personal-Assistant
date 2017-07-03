package com.zucc.wl1145_mjy1136.personalassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;

public class MainActivity extends AppCompatActivity {
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
        openButton = (Button) findViewById(R.id.button_more_main);
        closeButton = (Button) findViewById(R.id.button_close);
        openButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSlidingMenu.open();
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



















    }
}
