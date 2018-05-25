package com.ascend.wangfeng.wifimanage.delegates.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
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

import butterknife.BindView;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class UserDelegate extends BottomItemDelegate{

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

    private Person mPerson;
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

        mPerson = (Person) getArguments().getSerializable("person");
        initPerson();
        initDevices();
        //initHistory();
        Integer[][] data = new Integer[7][];
        //构造假数据
        for(int i = 0;i <7; i++){
            Integer[] column = new Integer[24];
            for(int j= 0;j <24; j++){
                column[j] = (int)(Math.random()*4);
            }
            data[i] = column;
        }
        mGithub.setData(data);
    }
    private void initPerson() {
    /*    PersonDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        mPerson = dao.queryBuilder().where(PersonDao.Properties.Id.eq(1)).unique();
        if (mPerson!=null){
        mTvName.setText(mPerson.getName());
        mCimgIcon.setImage(Icon.getImgUrl(mPerson.getImgUrl()));}*/
        //mTvDesc.setText();
    }

    private void initHistory() {
        initChart(mBarChart);
        setData();
    }

    private void setData() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorAccent));
        colors.add(getResources().getColor(R.color.colorOrange));
        colors.add(getResources().getColor(R.color.colorBlue));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int j = 0;j<2;j++){
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

            for (int i = 1; i <7; i++) {
                float mult = (24 + 1);
                float val = (float) (Math.random() * mult);
                yVals1.add(new BarEntry(i, val));

            }
            BarDataSet set1;
            set1 = new BarDataSet(yVals1, "设备"+j);
            set1.setDrawIcons(false);

            set1.setColor(colors.get(j));
            dataSets.add(set1);
        }
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);

        float groupSpace = .2f;
        float barWidth = (1f-.2f)/2*8/10;
        float barSpace = (1f-.2f)/2*2/10;
        data.setBarWidth(barWidth);
        data.groupBars(.5f,groupSpace,barSpace);

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
                int days = 7- (int) value;
                if (days ==0) {
                    return "当天";
                }else {
                    return days+"天前";
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
                return (int)value + "h";
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

    private void initDevices() {
        /*if (mPerson != null) {
            // 通过人员id 获取关联设备
            PersonDevicesMapDao mapDao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDevicesMapDao();
            List<PersonDevicesMap> maps = mapDao.queryBuilder().where(PersonDevicesMapDao.Properties.PId.eq(mPerson.getId())).list();
            DeviceDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getDeviceDao();
            ArrayList<Device> devices = new ArrayList<>();
            for (PersonDevicesMap map : maps) {
                Device devcice = dao.queryBuilder().where(DeviceDao.Properties.Id.eq(map.getDId())).unique();
                devices.add(devcice);
            }

            DeviceSquareAdapter adapter = new DeviceSquareAdapter(devices);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
            mRvDevices.setLayoutManager(manager);
            mRvDevices.setAdapter(adapter);
            mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                    .getColor(android.R.color.white), 3));
        }*/
    }
}
