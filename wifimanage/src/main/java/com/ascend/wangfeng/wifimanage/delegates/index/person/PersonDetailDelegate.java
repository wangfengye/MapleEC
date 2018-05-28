package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.bean.Liveness;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.ascend.wangfeng.wifimanage.views.GithubActivityView;
import com.github.mikephil.charting.charts.BarChart;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonDetailDelegate extends LatteDelegate {

    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.rv_devices)
    RecyclerView mRvDevices;
    @BindView(R.id.gt_history)
    GithubActivityView mGtHistory;

    private Person mPerson;


    public static PersonDetailDelegate newInstance(Bundle args) {
        PersonDetailDelegate fragment = new PersonDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_person_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mIcEdit.setVisibility(View.VISIBLE);
        mIcEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PersonEditDelegate.PERSON, mPerson);
                start(PersonEditDelegate.newInstance(bundle));
            }
        });
        mToolbarTitle.setText("成员详情");
        mPerson = (Person) getArguments().getSerializable("person");
        initPerson();
        initDevices();
        initHistory();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 编辑人员后返回刷新内容
            initPerson();
        }
    }

    private void initPerson() {
        mTvName.setText(mPerson.getName());
        mCimgIcon.setImage(Icon.getImgUrl(mPerson.getImgUrl()));
        //mTvDesc.setText();
    }

    private void initHistory() {
        /*柱状图列表*/
        //initChart(mBarChart);
        //setData();
        initGithubView();
    }

    private void initGithubView() {
        Client.getInstance().getLivenessesByPId(mPerson.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<List<Liveness>>>() {
                    @Override
                    public void accept(Response<List<Liveness>> response) throws Exception {
                        Integer[][] data = new Integer[7][];
                        //构造假数据
                        for(int i = 0;i <7; i++){
                            Integer[] column = new Integer[24];
                            for(int j= 0;j <24; j++){
                                int index  =i*7+j;
                                if (response.getData().size()>index)
                                column[j] = response.getData().get(index).getAvalue() ;
                                else column[j] = response.getData().get(0).getAvalue() ;
                            }
                            data[i] = column;
                        }
                        mGtHistory.setData(data);
                    }
                });
    }

    private void setData() {
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(getResources().getColor(R.color.colorAccent));
//        colors.add(getResources().getColor(R.color.colorOrange));
//        colors.add(getResources().getColor(R.color.colorBlue));
//        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//        for (int j = 0;j<2;j++){
//            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//
//            for (int i = 1; i <7; i++) {
//                float mult = (24 + 1);
//                float val = (float) (Math.random() * mult);
//                yVals1.add(new BarEntry(i, val));
//
//            }
//            BarDataSet set1;
//            set1 = new BarDataSet(yVals1, "设备"+j);
//            set1.setDrawIcons(false);
//
//            set1.setColor(colors.get(j));
//            dataSets.add(set1);
//        }
//        BarData data = new BarData(dataSets);
//        data.setValueTextSize(10f);
//
//        float groupSpace = .2f;
//        float barWidth = (1f-.2f)/2*8/10;
//        float barSpace = (1f-.2f)/2*2/10;
//        data.setBarWidth(barWidth);
//        data.groupBars(.5f,groupSpace,barSpace);
//
//        mBarChart.setData(data);
    }

    private void initChart(BarChart chart) {
       /* chart.setDrawBarShadow(false);
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
        l.setXEntrySpace(4f);*/
    }

    private void initDevices() {
        Client.getInstance().getDevicesByPId(mPerson.getId())
                .subscribe(new Consumer<Response<List<Device>>>() {
                    @Override
                    public void accept(Response<List<Device>> response) throws Exception {
                        DeviceSquareAdapter adapter = new DeviceSquareAdapter(response.getData());
                        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                        mRvDevices.setLayoutManager(manager);
                        mRvDevices.setAdapter(adapter);
                        mRvDevices.addItemDecoration(BaseDecoration.create(getResources()
                                .getColor(android.R.color.white), 3));
                    }
                });

    }
}
