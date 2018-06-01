package com.ascend.wangfeng.wifimanage.delegates.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceDetailDelegate;
import com.ascend.wangfeng.wifimanage.delegates.index.person.DeviceSquareAdapter;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.ascend.wangfeng.wifimanage.views.GithubActivityView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class UserDelegate extends BottomItemDelegate {

    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    //@BindView(R.id.bar_history)
    BarChart mBarChart;
    @BindView(R.id.github)
    GithubActivityView mGithub;
    @BindView(R.id.rl_add)
    RelativeLayout mRlAdd;

    private Person mPerson;
    private ArrayList mDevices;
    private DeviceSquareAdapter mDeviceAdapter;

    public static UserDelegate newInstance(Bundle args) {
        UserDelegate fragment = new UserDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        Client.getInstance().getPersonWithAttention()
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onNext(Response<Person> response) {
                        if (response.getData() != null) {
                            mRlAdd.setVisibility(View.GONE);
                            mPerson = response.getData();
                            initView();
                            initData();
                        } else {
                            showAddAttention();
                        }
                    }
                });
    }
    @OnClick(R.id.btn_add)
    void clickBtnAdd(){
        start(new AttentionChoiceDelegate());
    }
    private void initData() {
        initPerson();
        initDevices();
        initHistory();
    }

    private void initDevices() {
        Client.getInstance().getDevicesByPId(mPerson.getPid())
                .subscribe(new MyObserver<Response<List<Device>>>() {
                    @Override
                    public void onNext(Response<List<Device>> response) {
                        mDevices.clear();
                        mDevices.addAll(response.getData());
                        mDeviceAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mDevices = new ArrayList<>();
        mDeviceAdapter = new DeviceSquareAdapter(mDevices);
        mDeviceAdapter.setListener(device -> {
            Bundle args = new Bundle();
            args.putSerializable(DeviceDetailDelegate.DEVICE, device);
            start(DeviceDetailDelegate.newInstance(args), SINGLETASK);
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRvDevices.setLayoutManager(manager);
        mRvDevices.setAdapter(mDeviceAdapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.white), 3));
    }

    // 无关注人员时情况;
    private void showAddAttention() {
        mRlAdd.setVisibility(View.VISIBLE);
    }

    private void initPerson() {
        if (mPerson != null) {
            mTvName.setText(mPerson.getPname());
            mCimgIcon.setImage(Icon.getImgUrl(mPerson.getPimage()));
        }
        // mTvDesc.setText();
    }

    private void initHistory() {
       /* initChart(mBarChart);
        setData();*/
        Client.getInstance().getLivenessesByPId(mPerson.getPid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Liveness>>>() {
                    @Override
                    public void onNext(Response<List<Liveness>> response) {
                        Integer[][] data = new Integer[7][];
                        for (int i = 0; i < 7; i++) {
                            Integer[] column = new Integer[24];
                            for (int j = 0; j < 24; j++) {
                                int index = i * 7 + j;
                                if (response.getData().size() > index)
                                    column[j] = response.getData().get(index).getAvalue();
                                else column[j] = response.getData().get(0).getAvalue();
                            }
                            data[i] = column;
                        }
                        mGithub.setData(data);
                    }
                });
    }

    private void setData() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorAccent));
        colors.add(getResources().getColor(R.color.colorOrange));
        colors.add(getResources().getColor(R.color.colorBlue));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int j = 0; j < 2; j++) {
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

            for (int i = 1; i < 7; i++) {
                float mult = (24 + 1);
                float val = (float) (Math.random() * mult);
                yVals1.add(new BarEntry(i, val));

            }
            BarDataSet set1;
            set1 = new BarDataSet(yVals1, "设备" + j);
            set1.setDrawIcons(false);

            set1.setColor(colors.get(j));
            dataSets.add(set1);
        }
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);

        float groupSpace = .2f;
        float barWidth = (1f - .2f) / 2 * 8 / 10;
        float barSpace = (1f - .2f) / 2 * 2 / 10;
        data.setBarWidth(barWidth);
        data.groupBars(.5f, groupSpace, barSpace);

        mBarChart.setData(data);
    }

    private void initChart(BarChart chart) {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int days = 7 - (int) value;
                if (days == 0) {
                    return "当天";
                } else {
                    return days + "天前";
                }
            }
        };
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(axisValueFormatter);

        IAxisValueFormatter custom = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "h";
            }
        };

        YAxis leftAxis = mBarChart.getAxisLeft();

        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setSpaceBottom(0f);

        mBarChart.getAxisRight().setEnabled(false);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }


}
