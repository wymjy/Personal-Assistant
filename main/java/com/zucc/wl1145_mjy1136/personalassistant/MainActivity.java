package com.zucc.wl1145_mjy1136.personalassistant;

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


import com.zucc.wl1145_mjy1136.personalassistant.calendar.CalendarTodayActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;
import com.zucc.wl1145_mjy1136.personalassistant.expense.ExpenseMainActivity;
import com.zucc.wl1145_mjy1136.personalassistant.db.UserDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.user.LoginActivity;
import com.zucc.wl1145_mjy1136.personalassistant.user.UserMainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO=1;
    public static final int CROP_PHOTO=2;

    private CalendarDataOperation oper;
    private List<MyCalendar> records;

    private CircleImageView userButton;
    private Uri imageUri;
    ImageView picture;
    private Button addPic;
    private Button openButton;
    private Button closeButton;
    private SlidingMenu mSlidingMenu;
    private TextView schedule;
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

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.
                        getExternalStorageDirectory(),"out_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                intent.putExtra("crop",true);
                intent.putExtra("scale",true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CROP_PHOTO);
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
        records = oper.getAllRecord();
        schedule=(TextView)findViewById(R.id.textview2_main) ;
        String conclusionText = "";
        if(records.size() == 0)
            conclusionText += "目前您还没有添加任何日程哦，赶快添加吧！";
        else
            conclusionText += "目前您共有" + records.size() + "项日程";
        schedule.setText(conclusionText);

    }

    @Override
    protected void onResume() {
        super.onResume();
        records = oper.getAllRecord();
        schedule=(TextView)findViewById(R.id.textview2_main) ;
        String conclusionText = "";
        if(records.size() == 0)
            conclusionText += "目前您还没有添加任何日程哦，赶快添加吧！";
        else
            conclusionText += "目前您共有" + records.size() + "项日程";
        schedule.setText(conclusionText);
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
