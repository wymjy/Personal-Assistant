package com.zucc.wl1145_mjy1136.personalassistant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import com.zucc.wl1145_mjy1136.personalassistant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mjy on 2017/7/2.
 */

public class CalendarDataOperation {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private Context mContext;
    private Cursor cursor;
    public static String[] alarmName={"喜剧之王","巴赫：G弦上的咏叹调","爱之梦","光辉岁月","Moonlight Shadow","朋友的酒"};
    public static int[] alarmResouces={R.raw.xijuzhiwang,R.raw.gyt,R.raw.azm,R.raw.ghsy,R.raw.mls,R.raw.pydj};

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public CalendarDataOperation(Context context) {
        mContext = context;
        helper = new MyDatabaseHelper(mContext);
        database = helper.getWritableDatabase();
//		Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    public String findMusicName(int id){
        for(int i=0; i<alarmResouces.length; i++){
            if(alarmResouces[i]==id)
                return alarmName[i];
        }
        return "无音乐";
    }
    //添加记录
    public  void addAffair(String sql,String[] args) {
        database.execSQL(sql, args);
    }

    //检索出当前日期的编号、名称和日期
    public List<MyCalendar> getAllRecord() {
        //查询出当天的记录
//		Cursor cursor = database.rawQuery("select * from cal1 where calendarDate = ?", new String[]{currentDate});
        //查询出所有的记录
        cursor = database.rawQuery("select * from cal1 where user_id = ? order by calendarDate desc;",
                new String[]{UserDataOperation.currentUser});
        MyCalendar cal = new MyCalendar();
        List<MyCalendar> records = new ArrayList<MyCalendar>();
        while (cursor.moveToNext()) {
            cal = new MyCalendar();
            cal.setCalendarNo(cursor.getString(0));//获取第一列的值,第一列的索引从0开始
            cal.setCalendarName(cursor.getString(1));//获取第二列的值
            cal.setDate(cursor.getString(2));//获取第三列的值
            cal.setTime(cursor.getString(3));
            cal.setPlace(cursor.getString(4));
            cal.setDescription(cursor.getString(5));
            cal.setRepetition(cursor.getString(6));
            cal.setAdvanceTime(cursor.getString(7));
            cal.setMusic(cursor.getString(8));
            cal.setValid(cursor.getString(9));
            records.add(cal);
            //Toast.makeText(mContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
        }
        return records;
    }

    public List<MyCalendar> getRecordByDate(String date) {
        cursor = database.rawQuery("select * from cal1 where user_id = ? and calendarDate = ? order by calendarDate desc;",
                new String[]{UserDataOperation.currentUser, date});
        MyCalendar cal = new MyCalendar();
        List<MyCalendar> records = new ArrayList<MyCalendar>();
        while (cursor.moveToNext()) {
            cal = new MyCalendar();
            cal.setCalendarNo(cursor.getString(0));//获取第一列的值,第一列的索引从0开始
            cal.setCalendarName(cursor.getString(1));//获取第二列的值
            cal.setDate(cursor.getString(2));//获取第三列的值
            cal.setTime(cursor.getString(3));
            cal.setPlace(cursor.getString(4));
            cal.setDescription(cursor.getString(5));
            cal.setRepetition(cursor.getString(6));
            cal.setAdvanceTime(cursor.getString(7));
            cal.setMusic(cursor.getString(8));
            cal.setValid(cursor.getString(9));
            records.add(cal);
            //Toast.makeText(mContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
        }
        return records;
    }

    //检索出最近添加的日程
    public int getLastRecord(){
        int result=-1;
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select max(calendarNo) from cal1";
        Cursor cursor = db.rawQuery(sql, null) ;
        if(cursor.moveToFirst()){
            result=cursor.getInt(0);
        }
        return result;
    }

    public void delete(String calendarNo) {
        database.execSQL("delete from cal1 where calendarNo = ?",new String[]{calendarNo});
    }

    public MyCalendar getRecord2(String calendarNo) {
        cursor = database.rawQuery("select * from cal1 where calendarNo = ?",new String[]{calendarNo});
        MyCalendar cal = new MyCalendar();
        while (cursor.moveToNext()) {
            cal.setCalendarNo(cursor.getString(0));//获取第一列的值,第一列的索引从0开始
            cal.setCalendarName(cursor.getString(1));//获取第二列的值
            cal.setDate(cursor.getString(2));//获取第三列的值
            cal.setTime(cursor.getString(3));
            cal.setPlace(cursor.getString(4));
            cal.setDescription(cursor.getString(5));
            cal.setRepetition(cursor.getString(6));
            cal.setAdvanceTime(cursor.getString(7));
            cal.setMusic(cursor.getString(8));
            cal.setValid(cursor.getString(9));
        }
        return cal;
    }

    public void updateDate(String calendarNo, String calendarName, String date, String time, String place, String description, String repetition, String advanceTime, String musicid) {
        database.execSQL("update cal1 set calendarName = ?, calendarDate = ?, calendarTime = ?, place = ?, calDescription = ?, repetition = ?, advanceTime = ?, music_id = ? where calendarNo = ?",
                new String[]{calendarName, date, time, place, description, repetition, advanceTime, musicid,calendarNo});
        close();
    }



    public void close() {
        if(helper != null)
            helper.close();
        if(cursor != null)
            cursor.close();
        if(database != null)
            database.close();
    }
}
