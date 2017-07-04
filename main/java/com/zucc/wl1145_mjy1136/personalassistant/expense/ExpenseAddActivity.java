package com.zucc.wl1145_mjy1136.personalassistant.expense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.user.DatabaseUserManager;

import java.util.Date;

/**
 * Created by wanglei on 2017/7/2.
 */
public class ExpenseAddActivity extends AppCompatActivity {
    private DatabaseExpenseManager dbHelper;
    private String mount_state;
    private long date_now;

    private EditText type;
    private EditText description;
    private EditText mount;
    private Spinner spinner;
    private Button save;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        type = (EditText)findViewById(R.id.type_add_expense);
        description = (EditText)findViewById(R.id.description_add_expense);
        mount = (EditText)findViewById(R.id.mount_add_expense);
        spinner = (Spinner)findViewById(R.id.spinner_add_expense);
        save = (Button)findViewById(R.id.save_add_expense);
        cancel = (Button)findViewById(R.id.cancel_add_expense);
        initSpinner();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_now = (new Date()).getTime();
                dbHelper = new DatabaseExpenseManager(ExpenseAddActivity.this);
                dbHelper.insert(DatabaseUserManager.currentUser,date_now,type.getText().toString(),mount_state,
                        description.getText().toString(), Double.valueOf(mount.getText().toString()));
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initSpinner() {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_add_expense,android.R.layout.simple_spinner_item);
        //设置Spinner每个条目的显示样式
        //3.声明一个ArrayAdapter并获取对象，用于配置Spinner显示的信息。
        //对应参数说明：1.上下文变量；2.要显示的字符串Array；3.Spinner的显示样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //为spinner绑定适配器，书写标题信息，绑定监听事件。
        spinner.setAdapter(adapter);
        //spinner.setPrompt("任务难度");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mount_state = arg2==0?"out":"in";
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
