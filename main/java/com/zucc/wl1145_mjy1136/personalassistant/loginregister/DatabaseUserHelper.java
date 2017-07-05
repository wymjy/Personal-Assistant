package com.zucc.wl1145_mjy1136.personalassistant.loginregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by wanglei on 2017/7/4.
 */
public class DatabaseUserHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "PA.db" ;	// 数据库名称
    public static final String CREATE_USERITEM="create table UserItem (" +
            "user_id varchar(20) primary key," +
            "password varchar(20)," ;
    private Context mContext;
    public DatabaseUserHelper(Context context){
        super(context, DATABASENAME, null, 1);
        mContext=context;
    }

    public void insert(String user_id, String password) {
        SQLiteDatabase db = super.getWritableDatabase(); 	// 取得数据库操作对象
        ContentValues cv = new ContentValues() ;		// 定义ContentValues对象
        // 设置字段内容
        cv.put("user_id", user_id) ;
        cv.put("password",password);
        db.insert("UserItem", null, cv) ;			// 增加操作
        db.close() ;				// 关闭数据库操作
    }

    public String checkLogin(String user_id, String password){
        SQLiteDatabase db = this.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select password from ExpenseItem where user_id="+user_id;	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            if(cursor.getString(cursor.getColumnIndex("password"))==password)
                return "yes";
            else
                return "密码不正确！";
        }
        else
            return "用户不存在！";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
