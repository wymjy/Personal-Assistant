package com.zucc.wl1145_mjy1136.personalassistant.expense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.wl1145_mjy1136.personalassistant.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by wanglei on 2017/7/3.
 */
public class ExpenseCheckActivity extends AppCompatActivity{
    private MyDatabaseHelper dbHelper;
    private ExpenseListItem expenseListItem;
    private Button back;
    private Button delete;
    private TextView type;
    private TextView name;
    private TextView date;
    private TextView description;
    private TextView mount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_expense);

        //取出intent对象的的extras数据
        final long itemdate = getIntent().getExtras().getLong("itemdate");
        dbHelper = new MyDatabaseHelper(ExpenseCheckActivity.this);
        expenseListItem = dbHelper.queryByDate(itemdate);

        back = (Button)findViewById(R.id.back_check_expense);
        delete = (Button)findViewById(R.id.delete_check_expense);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteByDate(itemdate);
                //反馈
                Toast.makeText(ExpenseCheckActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                ExpenseCheckActivity.this.finish();
            }
        });
        type = (TextView)findViewById(R.id.type_check_expense);
        name = (TextView)findViewById(R.id.name_check_expense);
        date = (TextView)findViewById(R.id.date_check_expense);
        description = (TextView)findViewById(R.id.description_check_expense);
        mount = (TextView)findViewById(R.id.mount_check_expense);

        type.setText(expenseListItem.getItem_mount_state()=="in"?"收入":"支出");
        name.setText("款项："+expenseListItem.getItem_text());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date d = new Date(itemdate);
        date.setText(sdf.format(d));
        description.setText("备注："+expenseListItem.getComment());
        mount.setText("金额："+expenseListItem.getItem_mount());
    }
}
