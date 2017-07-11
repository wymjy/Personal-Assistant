package com.zucc.wl1145_mjy1136.personalassistant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseListItem;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wanglei on 2017/7/2.
 */
public class ExpenseDataOperation {
    private SQLiteOpenHelper helper;
    //private SQLiteDatabase database;
    private Context mContext;
    public ExpenseDataOperation(Context context){
        mContext = context;
        helper = new MyDatabaseHelper(mContext);
        //database = helper.getWritableDatabase();
    }

    public void insert(/*int image,*/String user_id, long date, String type, String mount_state , String comment ,double mount) {
        SQLiteDatabase db = helper.getWritableDatabase(); 	// 取得数据库操作对象
        ContentValues cv = new ContentValues() ;		// 定义ContentValues对象
        // 设置字段内容
        //cv.put("image",image);
        cv.put("user_id", user_id);
        cv.put("date", date) ;
        cv.put("class_text",type);
        cv.put("mount_state",mount_state);
        cv.put("comment",comment);
        cv.put("mount",mount);
        db.insert("ExpenseItem", null, cv) ;			// 增加操作
        db.close() ;				// 关闭数据库操作
    }

    public void deleteByDate(long date){
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "delete from ExpenseItem where date="+date ;
        db.execSQL(sql);
        db.close();
    }

    public ExpenseListItem queryByDate(long date){
        ExpenseListItem result = new ExpenseListItem();
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
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

    public Map<Long,ExpenseListItem> queryByUser(String user_id){
        ExpenseListItem listItem;
        Map<Long,ExpenseListItem> result = new TreeMap();
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select * from ExpenseItem where user_id='"+user_id+"'" ;	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            do{
                listItem=new ExpenseListItem();
                listItem.setType(ExpenseListItem.ITEM);
                listItem.setItem(//cursor.getInt(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("class_text")),
                        cursor.getString(cursor.getColumnIndex("comment")),
                        cursor.getDouble(cursor.getColumnIndex("mount")),
                        cursor.getString(cursor.getColumnIndex("mount_state")),
                        cursor.getLong(cursor.getColumnIndex("date")));
                result.put(listItem.get_date(),listItem);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
    public Map<Long,Map<String,Double>> queryByUserGroup(){
        Map<Long,Map<String,Double>> result = new TreeMap();
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select date/1000/60/60/24, sum(mount), mount_state from ExpenseItem where user_id='"
                +UserDataOperation.currentUser+"' group by mount_state,date/1000/60/60/24;" ;	// 定义SQL
        Cursor cursor = db.rawQuery(sql, null) ;		// 不设置查询参数
        if(cursor.moveToFirst()){
            do{
                Map<String, Double> inmap = new TreeMap();
                inmap.put(cursor.getString(2),cursor.getDouble(1));
                if(result.containsKey(cursor.getLong(0)))
                    result.get(cursor.getLong(0)).putAll(inmap);
                else
                    result.put(cursor.getLong(0), inmap);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
    public double maxMount(){
        SQLiteDatabase db = helper.getReadableDatabase();	// 取得SQLiteDatabase
        String sql = "select max(t.a) from (select sum(mount) as a from ExpenseItem where user_id='"
                +UserDataOperation.currentUser+"'group by mount_state,date/1000/60/60/24) as t" ;
        Cursor cursor = db.rawQuery(sql, null) ;
        if(cursor.moveToFirst()){
            return cursor.getDouble(0);
        }
        cursor.close();
        return 0;
    }

    public Map<String,Double> countMount(){
        Map<String,Double> result = new TreeMap();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select mount_state,sum(mount) from ExpenseItem where user_id='"
                +UserDataOperation.currentUser+ "' group by mount_state";
        Cursor cursor = db.rawQuery(sql, null) ;
        if(cursor.moveToFirst()){
            do{
                result.put(cursor.getString(0),cursor.getDouble(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

}
