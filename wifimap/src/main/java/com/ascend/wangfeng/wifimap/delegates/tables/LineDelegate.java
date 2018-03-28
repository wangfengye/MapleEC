package com.ascend.wangfeng.wifimap.delegates.tables;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimap.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;

/**
 * Created by fengye on 2018/3/16.
 * email 1040441325@qq.com
 */

public class LineDelegate extends LatteDelegate {
    @BindView(R.id.chart)
    LineChart mChart;

    @Override
    public Object setLayout() {
        return R.layout.delegate_line;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initChart();
        initData();
        initThread();
    }

    private void initThread() {
        ExecutorService theardPool = Executors.newCachedThreadPool();
        theardPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initChart() {
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.setDescription(getString(R.string.active_line));
        mChart.setNoDataTextDescription(getString(R.string.table_empty_hint));
        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setVisibleXRangeMaximum(10);
        mChart.setMaxVisibleValueCount(1);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);
        xAxis.setAxisMinValue(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setDrawLabels(true);
        yAxis.setDrawGridLines(true);
        YAxis yAxis1 = mChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setDrawGridLines(true);
        mChart.animateX(2000);


    }

    private void initData() {
        ArrayList<Entry> values = new ArrayList<>();
        LineDataSet set;
        set = new LineDataSet(values, "Line-1");
        set.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -200;
            }
        });
        set.setColor(Color.BLUE);
        set.setCircleColor(Color.BLUE);
        set.setLineWidth(1f);
        set.setCircleRadius(3f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
    }

    private void addEntry() {
        LineData data = mChart.getData();

        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount()-1, (float) -(Math.random() * 100) - 10f), 0);
            data.notifyDataChanged();
            mChart.setVisibleXRange(20,20);
            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries

            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
}
