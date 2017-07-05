package com.zucc.wl1145_mjy1136.personalassistant;

import android.content.Intent;
import android.preference.TwoStatePreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;
import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseMainActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CalendarDataOperation oper;
    private List<MyCalendar> records;
    private Button openButton;
    private Button closeButton;
    private SlidingMenu mSlidingMenu;
    private TextView schedule;
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

        //
        buttonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExpenseMainActivity.class);
                startActivity(intent);
            }
        });


        oper = new CalendarDataOperation(this);
        records = oper.getAllRecord();
        schedule=(TextView)findViewById(R.id.textview2_main) ;
        String conclusionText = "";
        if(records.size() == 0)
            conclusionText += "目前您还没有添加任何日程哦，赶快添加吧！";
        else
            conclusionText += "目前您共有" + records.size() + "项日程";
        schedule.setText(conclusionText);


    }
}
