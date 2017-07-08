package com.zucc.wl1145_mjy1136.personalassistant.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.UserDataOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2017/7/4.
 */
public class UserMainActivity extends AppCompatActivity{
    private TextView user_name;
    private CircleImageView head;
    private Button quit;

    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    public static final int CROP_PHOTO=2;
    private File outputImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        head = (CircleImageView)findViewById(R.id.user_head);
        user_name = (TextView)findViewById(R.id.user_name);
        quit = (Button) findViewById(R.id.user_quit);
        user_name.setText(UserDataOperation.currentUser);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataOperation.currentUser="";
                finish();
            }
        });
        outputImage = new File(Environment.
                getExternalStorageDirectory()+"/PA_UserHead","_"+UserDataOperation.currentUser+".jpg");
        if(outputImage.exists())
            try{
                imageUri = Uri.fromFile(outputImage);
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                head.setImageBitmap(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        head.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }break;
            default: break;
        }
    }
}
