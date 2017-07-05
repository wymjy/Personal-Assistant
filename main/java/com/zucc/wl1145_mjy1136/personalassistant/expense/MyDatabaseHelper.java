package com.zucc.wl1145_mjy1136.personalassistant.expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanglei on 2017/7/2.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "PA.db" ;	// 数据库名称
    public static final String CREATE_EXPENSEITEM="create table ExpenseItem (" +
            "id integer primary key autoincrement," +
            "date long," +
            //"image integer," +
            "mount real," +
            "mount_state text," +
            "class_text text," +
            "comment text)";
    private Context mContext;
    public MyDatabaseHelper(Context context){
        super(context, DATABASENAME, null, 1);
        mContext=context;
    }

    public void insert(/*int image,*/ long date, String type, String mount_state , String comment ,double mount) {
        SQLiteDatabase db = super.getWritableDatabase(); 	// 取得数据库操作对象
        ContentValues cv = new ContentValues() ;		// 定义ContentValues对象
        // 设置字段内容
        //cv.put("image",image);
        cv.put("date", date) ;
        cv.put("class_text",type);
        cv.put("mount_state",mount_state);
        cv.put("comment",comment);
        cv.put("mount",mount);
        db.insert("ExpenseItem", null, cv) ;			// 增加操作
        db.close() ;				// 关闭数据库操作
    }

    public void deleteByDate(long date){
        SQLiteDatabase db = this.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "delete from ExpenseItem where date="+date ;
        db.execSQL(sql);
        db.close();
    }

    public ExpenseListItem queryByDate(long date){
        ExpenseListItem result = new ExpenseListItem();
        SQLiteDatabase db = this.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select * from ExpenseItem where date="+date ;	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            result.setItem(//cursor.getInt(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("class_text")),
                    cursor.getString(cursor.getColumnIndex("comment")),
                    cursor.getDouble(cursor.getColumnIndex("mount")),
                    cursor.getString(cursor.getColumnIndex("mount_state")),
                    cursor.getLong(cursor.getColumnIndex("date")));
        }
        return result ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXPENSEITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
