package com.zucc.wl1145_mjy1136.personalassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {
    private Button share_btn;
    private Button share_back_btn;
    private EditText share_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        share_back_btn=(Button)findViewById(R.id.share_back_btn);
        share_btn=(Button)findViewById(R.id.share_btn);
        share_edt=(EditText) findViewById(R.id.share_edt);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, share_edt.getText().toString());
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"share"));
            }
        });


        share_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareActivity.this.finish();
            }
        });


    }

}
