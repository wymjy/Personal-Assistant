package com.zucc.wl1145_mjy1136.personalassistant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zucc.wl1145_mjy1136.personalassistant.CheckValid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjy on 2017/7/2.
 */

public class CalendarDataOperation {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private Context mContext;
    private Cursor cursor;
    public static int recordCount = 1;

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public CalendarDataOperation(Context context) {
        mContext = context;
        helper = new MyDatabaseHelper(mContext);
        database = helper.getWritableDatabase();
//		Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show();
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
            cal.setValid(cursor.getString(8));
            records.add(cal);
        }
//		Toast.makeText(mContext, cal.getCalendarName(), Toast.LENGTH_SHORT).show();
        return records;
    }

    //检索出当前日期的编号、名称和日期
    public List<MyCalendar> getTodayRecord() {
        CheckValid check = new CheckValid();
        String currentDate = check.getCurrentDate();
        //查询出当天的记录
        cursor = database.rawQuery("select * from cal1 where calendarDate = ? and user_id = ?",
                new String[]{currentDate, UserDataOperation.currentUser});
        //查询出所有的记录
//		cursor = database.rawQuery("select * from cal1 order by calendarDate desc;",null);
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
            cal.setValid(cursor.getString(8));
            records.add(cal);
        }
//		Toast.makeText(mContext, cal.getCalendarName(), Toast.LENGTH_SHORT).show();
        return records;
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
            cal.setValid(cursor.getString(8));
        }
        return cal;
    }

    public void updateDate(String calendarNo, String calendarName, String date, String time, String place, String description, String repetition, String advanceTime) {
        database.execSQL("update cal1 set calendarName = ?, calendarDate = ?, calendarTime = ?, place = ?, calDescription = ?, repetition = ?, advanceTime = ? where calendarNo = ?",new String[]{calendarName, date, time, place, description, repetition, advanceTime,calendarNo});
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
