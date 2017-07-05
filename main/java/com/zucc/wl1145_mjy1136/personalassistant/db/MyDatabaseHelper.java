package com.zucc.wl1145_mjy1136.personalassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mjy on 2017/7/2.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "PA.db" ;	// 数据库名称
    public static final String CREATE_USERITEM="create table UserItem (" +  //用户表
            "user_id varchar(20) primary key," +
            "password varchar(20))" ;
    public static final String CREATE_EXPENSEITEM="create table ExpenseItem (" +    //收支表
            "id integer primary key autoincrement," +
            "user_id varchar(20),"+
            "date long," +
            //"image integer," +
            "mount real," +
            "mount_state text," +
            "class_text text," +
            "comment text," +
            "FOREIGN KEY (user_id) REFERENCES UserItem(user_id))";
    public static final String CREATE_CALENDAR = "create table cal1(" + //日程表
            "calendarNo integer primary key autoincrement," +
            "user_id varchar(20)," +
            "calendarName text," +
            "calendarDate date," +
            "calendarTime time," +
            "place text," +
            "calDescription text," +
            "repetition integer," +
            "advanceTime integer," +
            "valid integer " +
            "constraint c1 check ( valid in (1,0) )," +
            "FOREIGN KEY (user_id) REFERENCES UserItem(user_id));";
//	public static final String CREATE_CALENDAR = "create table cal1(calendarNo integer primary key,calendarName text,repetition integer,advanceTime integer);";

    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASENAME, null, 1);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //创建表
        try {
            db.execSQL(CREATE_USERITEM);
            db.execSQL(CREATE_EXPENSEITEM);
            db.execSQL(CREATE_CALENDAR);
//		 Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
