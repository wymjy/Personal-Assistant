package com.zucc.wl1145_mjy1136.personalassistant.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by wanglei on 2017/7/4.
 */
public class DatabaseUserManager extends SQLiteOpenHelper {
    private static final String DATABASENAME = "PA2.db" ;	// 数据库名称
    public static final String CREATE_USERITEM="create table UserItem (" +
            "user_id varchar(20) primary key," +
            "password varchar(20))" ;
    private Context mContext;
    public static String currentUser = "";
    public DatabaseUserManager(Context context){
        super(context, DATABASENAME, null, 1);
        mContext=context;
    }

    public String insertRegi(String user_id, String password, String password2) {
        if(user_id.equals(""))
            return "用户名不能为空！";
        if(!password.equals(password2))
            return "密码不一致！";
        SQLiteDatabase db = super.getWritableDatabase(); 	// 取得数据库操作对象
        String sql = "select * from UserItem where user_id='"+user_id+"'";	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            return "用户已存在！";
        }
        ContentValues cv = new ContentValues() ;		// 定义ContentValues对象
        // 设置字段内容
        cv.put("user_id", user_id) ;
        cv.put("password",password);
        db.insert("UserItem", null, cv) ;			// 增加操作
        db.close() ;				// 关闭数据库操作
        return "注册成功！";
    }

    public String checkLogin(String user_id, String password){
        if(user_id.equals(""))
            return "用户名不能为空！";
        SQLiteDatabase db = this.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select password from UserItem where user_id='"+user_id+"'";	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            if(cursor.getString(cursor.getColumnIndex("password")).equals(password))
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
