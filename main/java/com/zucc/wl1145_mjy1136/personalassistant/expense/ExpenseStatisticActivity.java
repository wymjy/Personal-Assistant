package com.zucc.wl1145_mjy1136.personalassistant.expense;

import com.zucc.wl1145_mjy1136.personalassistant.R;
import com.zucc.wl1145_mjy1136.personalassistant.db.ExpenseDataOperation;
import com.zucc.wl1145_mjy1136.personalassistant.db.UserDataOperation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ExpenseStatisticActivity extends ActionBarActivity {

    private ExpenseDataOperation expenseDataOperation;
    private LineChartView chart;
    private LineChartData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_expense);
        chart = (LineChartView) findViewById(R.id.chart);
        generateTempoData();
    }

        private void generateTempoData() {
            expenseDataOperation = new ExpenseDataOperation(this);
            float tempoRange = (float)expenseDataOperation.maxMount()*1.2f;

            Line line;
            List<PointValue> valuesIn;
            List<PointValue> valuesOut;
            List<Line> lines = new ArrayList<>();

            valuesIn = new ArrayList<>();
            valuesOut = new ArrayList<>();
            Map<Long,Map<String,Double>> map;
            map = expenseDataOperation.queryByUserGroup();
            Set<Map.Entry<Long,Map<String,Double>>> entrySet = map.entrySet();
            int j = 1;
            for (Map.Entry<Long,Map<String,Double>> entry : entrySet) {
                if(entry.getValue().get("in")==null)
                    valuesIn.add(new PointValue(j, 0));
                else
                    valuesIn.add(new PointValue(j, entry.getValue().get("in").floatValue()));
                if(entry.getValue().get("out")==null)
                    valuesOut.add(new PointValue(j, 0));
                else
                    valuesOut.add(new PointValue(j, entry.getValue().get("out").floatValue()));
                j++;
            }

            line = new Line(valuesOut);
            line.setColor(Color.GRAY);
            line.setHasPoints(true);
            line.setHasLabels(true);
            line.setCubic(true);
            line.setFilled(true);
            line.setStrokeWidth(1);
            lines.add(line);
            line = new Line(valuesIn);
            line.setColor(ChartUtils.COLOR_RED);
            line.setHasPoints(true);
            line.setHasLabels(true);
            line.setCubic(true);
            line.setFilled(true);
            line.setStrokeWidth(1);
            lines.add(line);

            data = new LineChartData(lines);

            List<AxisValue> axisValues1 = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
            j=1;
            for (Map.Entry<Long,Map<String,Double>> entry : entrySet){
                Date date=new Date(entry.getKey()*1000*60*60*24);
                axisValues1.add(new AxisValue(j++).setLabel(sdf.format(date)));
            }
            Axis distanceAxis = new Axis(axisValues1).setName("日期")
                    .setTextColor(ChartUtils.COLOR_ORANGE).setMaxLabelChars(4);
            distanceAxis.setHasLines(true);
            distanceAxis.setHasTiltedLabels(true);
            data.setAxisXBottom(distanceAxis);

            List<AxisValue> axisValues = new ArrayList<>();
            for (int i = 0; i < tempoRange; i += tempoRange/10) {
                axisValues.add(new AxisValue(i).setLabel(String.valueOf(i)));
            }

            Axis tempoAxis = new Axis(axisValues).setName("金额 [￥]").setHasLines(true).setMaxLabelChars(4)
                    .setTextColor(ChartUtils.COLOR_RED);
            data.setAxisYLeft(tempoAxis);

            // Set data
            chart.setLineChartData(data);

            Viewport v = chart.getMaximumViewport();
            v.set(v.left, tempoRange, v.right, 0);
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
        }

}
