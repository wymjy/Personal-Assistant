package com.zucc.wl1145_mjy1136.personalassistant.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarSpecificActivity;
import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;

import java.util.Calendar;

public class AlarmActivity extends Activity {
    private MediaPlayer alarmMusic;
    private int calendarNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        calendarNo = getIntent().getExtras().getInt("calNo");
        CalendarDataOperation oper = new CalendarDataOperation(this);
        MyCalendar calendar = oper.getRecord2(calendarNo + "");
        String calendarName = calendar.getCalendarName();
        if(calendarName.length() == 0)
            calendarName = "未命名日程";

        alarmMusic = MediaPlayer.create(this, Integer.parseInt(calendar.getMusic()));
        alarmMusic.setLooping(true);
        //播放音乐
        alarmMusic.start();
        new AlertDialog.Builder(AlarmActivity.this).setTitle("提醒")
                .setMessage("您的"+calendarName+"日程时间到了")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        alarmMusic.stop();
                        Intent intent = new Intent();
                        intent.setClass(AlarmActivity.this, CalendarTodayActivity.class);
                        intent.putExtra("calendarNo", calendarNo + "");
                        startActivity(intent);
                        AlarmActivity.this.finish();
                    }
                }).show();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        Log.v("alarmTag", "闹钟事件已触发，时间 ：" + cal.getTime());
//		this.finish();



    }


}

