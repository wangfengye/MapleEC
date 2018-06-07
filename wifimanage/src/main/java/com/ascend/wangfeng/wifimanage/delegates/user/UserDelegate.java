package com.ascend.wangfeng.wifimanage.delegates.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.latte.util.TimeUtil;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
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

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class UserDelegate extends BottomItemDelegate {
    private static final Long WEEK_TIME = 7 * 24 * 60 * 60 * 1000L;// 一周对应的时间戳
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;

    @BindView(R.id.github)
    GithubActivityView mGithub;
    @BindView(R.id.rl_add)
    RelativeLayout mRlAdd;
    @BindView(R.id.tv_liveness_title)
    TextView mTvLivenessTitle;
    private Person mPerson;
    private ArrayList<Device> mDevices;
    private DeviceSquareAdapter mDeviceAdapter;
    private Long mTime = 0L;
    private Device mDevice;// 记录当前展示的device;

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
        mTime = System.currentTimeMillis();
        initView();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Client.getInstance().getPersonWithAttention()
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onSuccess(Response<Person> response) {
                        if (response.getData() != null) {
                            mRlAdd.setVisibility(View.GONE);
                            mLlContent.setVisibility(View.VISIBLE);
                            mPerson = response.getData();
                            initData();
                        } else {
                            showAddAttention();
                        }
                    }
                });
    }

    @OnClick(R.id.btn_add)
    void clickBtnAdd() {
        start(new AttentionChoiceDelegate());
    }

    private void initData() {
        initPerson();
        initDevices();

    }

    private void initDevices() {
        Client.getInstance().getDevicesByPid(mPerson.getPid())
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Device>>>() {
                    @Override
                    public void onSuccess(Response<List<Device>> response) {
                        mDevices.clear();
                        if (response.getData().size() > 0){
                            mDevice = response.getData().get(0);
                            mDevice.setSelected(true);
                        }
                        mDevices.addAll(response.getData());
                        mDeviceAdapter.notifyDataSetChanged();
                        initHistory(mTime, mDevice);
                    }
                });
    }

    private void initView() {
        mDevices = new ArrayList<>();
        mDeviceAdapter = new DeviceSquareAdapter(mDevices);
        mDeviceAdapter.setListener(device -> {
            // 切换加载的图表;
            mDevice = device;
            initHistory(mTime, device);
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRvDevices.setLayoutManager(manager);
        mRvDevices.setAdapter(mDeviceAdapter);
        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.white, getActivity().getTheme()), 3));

        mGithub.mListener = i -> {
            if (i == GithubActivityView.LEFT) {
                if (System.currentTimeMillis() - mTime < 60 * 60 * 24 * 7 * 1000) {
                    Toast.makeText(getActivity(), "已划至最新时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                mTime += WEEK_TIME;
            } else {
                mTime -= WEEK_TIME;
            }

            initHistory(mTime, mDevice);
        };
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

    private void initHistory(Long time, Device device) {
        if (device == null) return;
        mTvLivenessTitle.setText(TimeUtil.formatWeek(time));
        Long weekstart = com.ascend.wangfeng.latte.util.TimeUtil.getFirstTimeOfWeek(mTime);
        Client.getInstance().getLivenesses(device.getDmac(), time)
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Liveness>>>() {
                    @Override
                    public void onSuccess(Response<List<Liveness>> response) {
                        Integer[][] data = new Integer[7][];
                        for (int i = 0; i < 7; i++) {
                            Integer[] column = new Integer[24];
                            for (int j = 0; j < 24; j++) {
                                int index = i * 24 + j;
                                for (int k = 0; k < response.getData().size(); k++) {
                                    if (response.getData().get(k).getTimeStamp()
                                            == weekstart + index * 60 * 60 * 1000) {
                                        column[j] = response.getData().get(k).getAvalue();
                                        break;
                                    }
                                }
                            }
                            data[i] = column;
                        }
                        mGithub.setData(data);
                    }
                });
    }

    private void setData(BarChart barChart) {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
        colors.add(getResources().getColor(R.color.colorOrange, getActivity().getTheme()));
        colors.add(getResources().getColor(R.color.colorBlue, getActivity().getTheme()));
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

        barChart.setData(data);
    }

    @SuppressWarnings("all")
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
        XAxis xAxis = chart.getXAxis();
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

        YAxis leftAxis = chart.getAxisLeft();

        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setSpaceBottom(0f);

        chart.getAxisRight().setEnabled(false);

        Legend l = chart.getLegend();
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
