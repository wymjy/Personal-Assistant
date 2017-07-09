package com.zucc.wl1145_mjy1136.personalassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.TwoStatePreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import android.widget.ImageButton;
import android.widget.Toast;


import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarAddActivity;
import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.ExpenseDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;
import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseAddActivity;
import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseMainActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.UserDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.user.LoginActivity;
import com.zucc.wl1145_mjy1136.personalassistant.user.UserMainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO=1;
    public static final int CROP_PHOTO=2;
    private File dirFirstFolder;
    private File outputImage;

    private CalendarDataOperation oper;
    private ExpenseDataOperation ex;
    private List<MyCalendar> records;
    private Map<String,Double> summap=null;

    private CircleImageView userButton;
    private Uri imageUri;
    ImageView picture;
    private Button addPic;
    private Button add_main;
    private Button openButton;
    private Button closeButton;
    private SlidingMenu mSlidingMenu;
    private TextView schedule;
    private TextView expense1;
    private TextView expense2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlidingMenu = new SlidingMenu(this, LayoutInflater
                .from(this).inflate(R.layout.activity_main, null), LayoutInflater
                .from(this).inflate(R.layout.left_fragment, null));
        setContentView(mSlidingMenu);//注意setContentView需要换为我们的SlidingMenu
        userButton = (CircleImageView) findViewById(R.id.head);
        addPic = (Button)findViewById(R.id.add_main);
        openButton = (Button) findViewById(R.id.button_more_main);
        closeButton = (Button) findViewById(R.id.button_close);
        picture = (ImageView)findViewById(R.id.picture_main);
        //用户头像
        dirFirstFolder = new File(Environment.
                getExternalStorageDirectory()+"/PA_UserHead");
        if(!dirFirstFolder.exists()) { //如果该文件夹不存在，则进行创建
            dirFirstFolder.mkdirs();//创建文件夹
        }
        outputImage = new File(dirFirstFolder,"_"+UserDataOperation.currentUser+".jpg");
        if(outputImage.exists())
            try{
                imageUri = Uri.fromFile(outputImage);
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                userButton.setImageBitmap(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserDataOperation.currentUser.equals("")){
                    Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //添加按钮
        add_main=(Button)findViewById(R.id.add_main);
        add_main.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("添加")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new String[] {"日程管理","个人收支管理"}, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        switch (which) {

                                            case 0:
                                                intent.setClass(MainActivity.this, CalendarAddActivity.class);
                                                startActivity(intent);
                                                break;
                                            case 1:
                                                intent.setClass(MainActivity.this,ExpenseAddActivity.class);
                                                startActivity(intent);
                                                break;
                                            default:
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        
        openButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSlidingMenu.open();
                //Toast.makeText(MainActivity.this, UserDataOperation.currentUser, Toast.LENGTH_SHORT).show();
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
        Button buttonQRcode =(Button)findViewById(R.id.item5_ce);
        Button buttonQuit = (Button)findViewById(R.id.item6_ce);
        Button buttonShare = (Button)findViewById(R.id.item8_ce);


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

        //扫一扫
        buttonQRcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WeChatCaptureActivity.class);
                startActivity(intent);
            }
        });

        //收支管理
        buttonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExpenseMainActivity.class);
                startActivity(intent);
            }
        });

        //分享
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });

        oper = new CalendarDataOperation(this);
        ex = new ExpenseDataOperation(this);
    }

    @Override
    protected void onResume() {
        records = oper.getAllRecord();
        schedule=(TextView)findViewById(R.id.textview2_main) ;
        String conclusionText = "";
        if(records.size() == 0)
            conclusionText += "目前您还没有添加任何日程哦，赶快添加吧！";
        else
            conclusionText += "目前您共有" + records.size() + "项日程";
        schedule.setText(conclusionText);
        summap=ex.countMount();
        expense1=(TextView)findViewById(R.id.textview3_main);
        conclusionText = "";
        if(summap.get("in")==null)
            conclusionText += "您还没有买过任何东西哦";
        else
            conclusionText += "共收入："+ summap.get("in");
        expense1.setText(conclusionText);
        expense2=(TextView)findViewById(R.id.textview4_main);
        conclusionText = "";
        if(summap.get("out")==null)
            conclusionText += "您还没有任何收入哦";
        else
            conclusionText += "共支出："+ summap.get("out");
        expense2.setText(conclusionText);
        try{
            //Toast.makeText(MainActivity.this, UserDataOperation.currentUser, Toast.LENGTH_SHORT).show();
            outputImage = new File(dirFirstFolder,"_"+UserDataOperation.currentUser+".jpg");
            imageUri = Uri.fromFile(outputImage);
            Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            userButton.setImageBitmap(bitmap);
        }catch(FileNotFoundException e) {
            userButton.setImageResource(R.drawable.touxiang);
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }break;
            default: break;
        }
    }
}
