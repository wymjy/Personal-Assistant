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

        alarmMusic = MediaPlayer.create(this, R.raw.xijuzhiwang);
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


//		// TODO Auto-generated method stub
//		myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		setNotiType(R.drawable.ic_launcher, "sdf");
//		this.finish();

    }

//	/* 发出Notification的method */
//	  private void setNotiType(int iconId, String text)
//	  {
//	    /* 建立新的Intent，作为点选Notification留言条时，
//	     * 会执行的Activity */
//	    Intent notifyIntent=new Intent(this,TaskEvaluateActivity.class);
//	    notifyIntent.putExtra("taskNo", 1);
//	    notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//	    /* 建立PendingIntent作为设定递延执行的Activity */
//	    PendingIntent appIntent=PendingIntent.getActivity(this,0,
//	                                                      notifyIntent,0);
//
//	    /* 建立Notication，并设定相关参数 */
//	    Notification myNoti=new Notification();
////	    Notification myNoti = new Notification(
////				R.drawable.ic_launcher, "This is ticker text",
////				System.currentTimeMillis());
//	    /* 设定statusbar显示的icon */
//	    myNoti.icon=iconId;
//	    /* 设定statusbar显示的文字讯息 */
//	    myNoti.tickerText=text;
//	    /* 设定notification发生时同时发出预设声音 */
//	    myNoti.defaults=Notification.DEFAULT_SOUND;
//	    /* 设定Notification留言条的参数 */
//	    myNoti.setLatestEventInfo(this,"MSN登入状态",text,appIntent);
//	    /* 送出Notification */
//	    myNotiManager.notify(1,myNoti);
//	  }
}

