package com.zucc.wl1145_mjy1136.personalassistant.expense;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.ExpenseDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.UserDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by wanglei on 2017/7/3.
 */
public class ExpenseMainActivity extends AppCompatActivity {

    private ExpenseDataOperation expenseDataOperation;
    private MyAdapter adapter;
    private Map<Long,ExpenseListItem> map=null;
    private Map<String,Double> summap=null;
    private LinkedList<ExpenseListItem> list=new LinkedList<ExpenseListItem>();
    private ListView listview;
    private Button back;
    private Button add;
    private TextView incomecount;
    private TextView outcomecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_expense);
        incomecount = (TextView)findViewById(R.id.textview3_expense_main);
        outcomecount = (TextView)findViewById(R.id.textview4_expense_main);
        back=(Button)findViewById(R.id.back_expense_main);
        add=(Button)findViewById(R.id.add_expense_main);
        expenseDataOperation=new ExpenseDataOperation(this);
        summap=expenseDataOperation.countMount();
        incomecount.setText("\t共计收入："+ summap.get("in"));
        outcomecount.setText("\t共计支出："+summap.get("out"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseMainActivity.this, ExpenseAddActivity.class);
                startActivity(intent);
            }
        });
        listview=(ListView) findViewById(R.id.listview_expense_main);
        initData();
        adapter=new MyAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //切换到详细的收支界面
                if(list.get(position).getType()==ExpenseListItem.ITEM) {
                    Intent intent = new Intent();
                    intent.setClass(ExpenseMainActivity.this, ExpenseCheckActivity.class);
                    intent.putExtra("itemdate", list.get(position).get_date());
                    //Toast.makeText(MainActivity.this, "" + list.get(position).get_date(), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });
    }

    //插入删除数据后返回页面及时更新
    @Override
    protected void onRestart() {
        super.onRestart();
        map.clear();
        list.clear();
        summap=expenseDataOperation.countMount();
        incomecount.setText("\t共计收入："+ summap.get("in"));
        outcomecount.setText("\t共计支出："+summap.get("out"));
        initData();
        listview.setAdapter(adapter);
    }

    public void initData(){
//        map = new TreeMap<Long,ExpenseListItem>();
        ExpenseListItem listItem;
        map = expenseDataOperation.queryByUser(UserDataOperation.currentUser);
        Set<Map.Entry<Long,ExpenseListItem>> entrySet=map.entrySet();
        long predate=0;
        double sum_income=0,sum_cost=0;
        for(Map.Entry<Long,ExpenseListItem> entry: entrySet){
            /*这里主要是把Map里按日期时间排好序的ListItem元素顺序放入一个线性表中
            顺序放入的同时插入分组条tag*/
            if(predate/(1000*3600*24)!=entry.getKey()/(1000*3600*24)&&predate!=0){
                //满足此条件时插入分组条tag，注意(predate!=0)这条件是边界值处理
                listItem=new ExpenseListItem();
                listItem.setType(ExpenseListItem.TAG);
                listItem.setTag(predate,sum_income,sum_cost);
                list.addFirst(listItem);
                sum_income=sum_cost=0;
            }
            if(entry.getValue().getItem_mount_state().equals("in"))
                sum_income+=entry.getValue().getItem_mount();
            else if(entry.getValue().getItem_mount_state().equals("out"))
                sum_cost+=entry.getValue().getItem_mount();
            predate=entry.getKey();
            list.addFirst(entry.getValue());
        }
        //边界值处理
        if(!list.isEmpty()) {
            listItem = new ExpenseListItem();
            listItem.setType(ExpenseListItem.TAG);
            listItem.setTag(predate, sum_income, sum_cost);
            list.addFirst(listItem);
        }
    }
    class MyAdapter extends BaseAdapter {
        private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public ExpenseListItem getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view=convertView;
            if(getItem(position).getType()== ExpenseListItem.TAG){
                view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.expense_list_item_tag, null);

                TextView textDate=(TextView)view.findViewById(R.id.ex_list_tag_date);
                TextView textSum=(TextView)view.findViewById(R.id.ex_list_tag_sum);
                Date date=new Date(getItem(position).get_date());
                textDate.setText(sdf.format(date));
                //tag上当天的支出收入和
                double cost=getItem(position).getTag_cost()
                        ,income=getItem(position).getTag_income();
                String result="";
                if(cost!=0)
                    result+="支出："+String.valueOf(cost);
                if(income!=0)
                    result+="\t\t收入："+String.valueOf(income);
                textSum.setText(result);
            }else if(getItem(position).getType()==ExpenseListItem.ITEM){
                view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.expense_list_item, null);
                //ImageView imageView=(ImageView)view.findViewById(R.id.list_item_image);
                TextView text=(TextView)view.findViewById(R.id.ex_list_item_text);
                TextView comment=(TextView)view.findViewById(R.id.ex_list_item_comment);
                TextView textMount=(TextView)view.findViewById(R.id.ex_list_item_mount);
                //图片及相应名称
                //imageView.setImageResource(getItem(position).getItem_image());
                text.setText(getItem(position).getItem_text());
                //判断备注textview的text是否为空串，是就把布局隐藏
                if(getItem(position).getComment().equals("")) {
                    comment.setVisibility(View.GONE);
                }
                else {
                    comment.setVisibility(View.VISIBLE);
                    comment.setText("备注："+getItem(position).getComment());
                }
                //金额数，如果是收入那就红色字体
                textMount.setText(Double.toString(getItem(position).getItem_mount()));
                if(getItem(position).getItem_mount_state().equals("in"))
                    textMount.setTextColor(getResources().getColor(R.color.incomeText));
            }
            return view;
        }
    }
}
