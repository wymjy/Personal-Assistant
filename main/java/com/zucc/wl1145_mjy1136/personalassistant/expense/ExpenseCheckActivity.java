package com.zucc.wl1145_mjy1136.personalassistant.expense;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.ExpenseDataOperation;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by wanglei on 2017/7/3.
 */
public class ExpenseCheckActivity extends AppCompatActivity{
    private ExpenseDataOperation expenseDataOperation;
    private ExpenseListItem expenseListItem;
    private Button share_expense_btn;
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
        expenseDataOperation = new ExpenseDataOperation(ExpenseCheckActivity.this);
        expenseListItem = expenseDataOperation.queryByDate(itemdate);
        share_expense_btn=(Button)findViewById(R.id.share_check_calendar);
        back = (Button)findViewById(R.id.back_check_expense);
        delete = (Button)findViewById(R.id.delete_check_expense);


        share_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, name.getText().toString()+type.getText().toString()+mount.getText().toString()+description.getText().toString());
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"share"));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataOperation.deleteByDate(itemdate);
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
