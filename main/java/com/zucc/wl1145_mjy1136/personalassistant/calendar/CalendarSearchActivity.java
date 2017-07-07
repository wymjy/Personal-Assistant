package com.zucc.wl1145_mjy1136.personalassistant.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.CalendarDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.MyCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarSearchActivity extends AppCompatActivity {
    private MyCalendar cal;
    private Calendar c;
    private CalendarDataOperation oper;
    private List<MyCalendar> records = new ArrayList<MyCalendar>();

    private EditText dateInput;
    private Button search;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_search);
        dateInput = (EditText)findViewById(R.id.search_edittext);
        search = (Button)findViewById(R.id.search_btn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oper = new CalendarDataOperation(CalendarSearchActivity.this);
                records = oper.getRecordByDate(dateInput.getText().toString());
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
                listView = (ListView)findViewById(R.id.ListView_search_calendar);
                listView.setAdapter(simpleAdapter);
            }
        });

    }
}
