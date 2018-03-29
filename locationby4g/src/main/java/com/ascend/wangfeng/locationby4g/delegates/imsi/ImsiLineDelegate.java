package com.ascend.wangfeng.locationby4g.delegates.imsi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.locationby4g.MainActivity;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.rxbus.RxBus;
import com.ascend.wangfeng.locationby4g.rxbus.Subscribe;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/3/22.
 * email 1040441325@qq.com
 */

public class ImsiLineDelegate extends LatteDelegate {
    public static final String TAG = ImsiLineDelegate.class.getSimpleName();
    @BindView(R.id.chart_imsi)
    LineChart mChartImsi;
    @BindView(R.id.ic_back)
    IconTextView mIvBack;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.btn_location)
    Button mBtnLocation;
    private boolean mIsLocation;
    private CellMeaureAckEntry mEntry;

    @OnClick(R.id.btn_location)
    void setBtnLocationClick() {
        if (!mEntry.isLocation()) {
            ((MainActivity) getProxyActivity()).getService().locaion(mImsi);
            mEntry.setLocation(true);
            mBtnLocation.setBackgroundColor(getResources().getColor(R.color.success));
            mBtnLocation.setText(R.string.unlocation);
        } else {
            ((MainActivity) getProxyActivity()).getService().unLocation(mImsi);
            mBtnLocation.setBackgroundColor(getResources().getColor(R.color.info));
            mBtnLocation.setText(R.string.location);
            mEntry.setLocation(false);
        }
    }

    private String mImsi;

    public static ImsiLineDelegate newInstance(String imsi) {
        final Bundle args = new Bundle();
        args.putString("Imsi", imsi);
        ImsiLineDelegate delegate = new ImsiLineDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_imsi_line;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mImsi = getArguments().getString("Imsi");
        initToolbar();
        initChart2(mChartImsi);
        // initTest();
        initData();
        initBtn();
        RxBus.getDefault().register(this);

    }

    private void initBtn() {
        if (mEntry.isLocation()) {
            mBtnLocation.setBackgroundColor(getResources().getColor(R.color.success));
            mBtnLocation.setText(R.string.unlocation);
        } else {
            mBtnLocation.setBackgroundColor(getResources().getColor(R.color.info));
            mBtnLocation.setText(R.string.location);
        }
    }

    //测试数据
    private void initTest() {
        updateChart(1522135340 - 1522080000, -89);
        updateChart(1522135350 - 1522080000, -22);
        updateChart(1522135359 - 1522080000, -34);
        updateChart(1522135377 - 1522080000, -82);
        updateChart(1522135377 - 1522080000, -82);
        updateChart(1522135387 - 1522080000, -82);
        updateChart(1522136237 - 1522080000, -82);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateChart(1522135340 - 1522080000, -76);
                    }
                });
            }
        };
        timer.schedule(task, 2000, 1000);
    }

    private void initData() {

        MainActivity activity = (MainActivity) getProxyActivity();
        ArrayList<CellMeaureAckEntry> entries = activity.getService().mEntries;
        for (CellMeaureAckEntry entry : entries) {
            if (entry.getImsi().equals(mImsi)) {
                // 添加历史数据
                mEntry = entry;
                for (int i = 0; i < entry.getFieldIntensitys().size(); i++) {
                    updateChart(entry.getTimeStamps().get(i).floatValue(), entry.getFieldIntensitys().get(i));
                }
                updateChart((float) entry.getTimestamp(), entry.getFieldIntensity());
                //设置是否监控
                mIsLocation = entry.isLocation();
                break;
            }
        }
    }

    @Subscribe()
    public void receiveCellMeaureAck(CellMeaureAckEvent event) {
        final CellMeaureAck data = event.getAck();
        if (data.getImsi().equals(mImsi)) {
            updateChart((float) data.getTimestamp(), data.getFieldIntensity());
        }
    }

    private void initToolbar() {
        mTvToolbarTitle.setText(mImsi);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImsiLineDelegate.this.pop();
            }
        });

    }

    private void initChart2(LineChart chart) {
        chart.setNoDataText("暂未数据");
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(8f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        // x轴刻度内容修改
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return " ";
            }

        });

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        leftAxis.setAxisMaxValue(0f);
        leftAxis.setAxisMinValue(-120f);
        rightAxis.setEnabled(false);
    }

    private void updateChart(float x, float y) {
        if (mChartImsi == null) return;

        if (mChartImsi.getData() == null) {
            createData(mChartImsi);
        }
        Entry entry = new Entry(mChartImsi.getData().getDataSetByIndex(0).getEntryCount(), y);

        mChartImsi.getData().addEntry(entry, 0);
        mChartImsi.getData().notifyDataChanged();
        mChartImsi.notifyDataSetChanged();
        mChartImsi.setVisibleXRangeMaximum(10);
        mChartImsi.moveViewToX(mChartImsi.getData().getXMax());
        mChartImsi.invalidate();

    }

    private void createData(LineChart chart) {
        String label = "IMSI";
        LineDataSet set = new LineDataSet(null, label);
        set.setDrawFilled(true);
        set.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -120;
            }
        });

        set.setFillAlpha(30);
        set.setCircleRadius(3f);
        set.setHighlightEnabled(false);
        set.setColor(getResources().getColor(R.color.colorAccent));
        set.setCircleColor(getResources().getColor(R.color.colorAccent));
        set.setDrawValues(true);
        YAxis leftAxis = chart.getAxisLeft();

        LineData data = new LineData();
        data.addDataSet(set);
        data.setDrawValues(true);
        chart.setData(data);
        chart.moveViewToX(data.getXMax());
    }

    @Override
    public boolean onBackPressedSupport() {
        ImsiLineDelegate.this.pop();
        return true;
    }

    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
