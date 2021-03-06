package com.zucc.wl1145_mjy1136.personalassistant.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarSearchActivity extends AppCompatActivity {

    private CalendarDataOperation oper;
    private List<MyCalendar> records;

    private EditText dateInput;
    private Button search;
    private Button search_btn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_search);
        dateInput = (EditText)findViewById(R.id.search_edittext);
        search = (Button)findViewById(R.id.search_btn);
        search_btn=(Button)findViewById(R.id.search_back_btn);
        listView = (ListView)findViewById(R.id.ListView_search_calendar);

        oper = new CalendarDataOperation(CalendarSearchActivity.this);

        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               records = oper.getRecordByDateDim(dateInput.getText().toString());
                initList(records);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 CalendarSearchActivity.this.finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records = oper.getRecordByDate(dateInput.getText().toString());
                initList(records);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //切换到详细的日常界面
                Intent intent = new Intent();
                intent.setClass(CalendarSearchActivity.this, CalendarSpecificActivity.class);
                intent.putExtra("calendarNo", records.get(position).getCalendarNo());
                startActivity(intent);
            }
        });
    }

    private void initList(List<MyCalendar> records){
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < records.size(); i ++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            if(records.get(i).getCalendarName().length() == 0)
                listItem.put("name", "未命名日程");
            else
                listItem.put("name", records.get(i).getCalendarName());
            listItem.put("date", records.get(i).getDate());
            listItem.put("calendarNo", records.get(i).getCalendarNo());
            listItems.add(listItem);
        }
        //创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(CalendarSearchActivity.this, listItems,
                R.layout.simple_item2,
                new String[]{"name","date"},
                new int[] {R.id.nameTextView2, R.id.dateTextView2} );
        listView.setAdapter(simpleAdapter);
    }
}
