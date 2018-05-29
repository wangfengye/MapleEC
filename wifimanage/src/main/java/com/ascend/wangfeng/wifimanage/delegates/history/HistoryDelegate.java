package com.ascend.wangfeng.wifimanage.delegates.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonDetailDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.utils.TimeUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 历史记录(动态)
 */

public class HistoryDelegate extends BottomItemDelegate {
    public static final String TAG = HistoryDelegate.class.getSimpleName();
    @BindView(R.id.tv_month_day)
    TextView mTvMonthDay;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.tv_lunar)
    TextView mTvLunar;
    @BindView(R.id.ib_calendar)
    ImageView mIbCalendar;
    @BindView(R.id.tv_current_day)
    TextView mTvCurrentDay;
    @BindView(R.id.fl_current)
    FrameLayout mFlCurrent;
    @BindView(R.id.rl_tool)
    RelativeLayout mRlTool;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private int mYear;
    private List<Event> mEvents;
    private EventAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_history;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initDate();
        initRv();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData(mCalendarView.getSelectedCalendar());
    }

    private void initData(Calendar calendar) {
        long time = TimeUtil.getTime(calendar.getYear(), calendar.getLeapMonth(), calendar.getDay());
        mCalendarView.getSelectedCalendar();
        Client.getInstance().getEvents(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Response<List<Event>>>() {
                    @Override
                    public void onNext(Response<List<Event>> response) {
                        mEvents.clear();
                        mEvents.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initRv() {
        mEvents = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new EventAdapter(mEvents);
        mAdapter.setClickListener(new EventAdapter.OnClickListener() {
            @Override
            public void Click(Person person) {
                Bundle args = new Bundle();
                args.putSerializable(PersonDetailDelegate.PERSON, person);
                getParentDelegate().start(PersonDetailDelegate.newInstance(args));
            }
        });
        mRvHistory.setAdapter(mAdapter);
        mRvHistory.setLayoutManager(manager);
        mRvHistory.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.textFour), 1));
    }

    private void initDate() {

        mYear = mCalendarView.getCurYear();
        mTvMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTvLunar.setText("今日");
        mTvYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mTvCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                mTvMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
                mTvYear.setText(String.valueOf(calendar.getYear()));
                mTvLunar.setText(calendar.getLunar());
                mTvCurrentDay.setText(String.valueOf(calendar.getDay()));
                mYear = calendar.getYear();
                if (mAdapter != null && mEvents != null)
                    initData(calendar);
            }
        });
        mCalendarView.setOnYearChangeListener(new CalendarView.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {
                mYear = year;
                mTvMonthDay.setText(String.valueOf(year));
            }
        });
        mTvMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                } else {
                    mCalendarView.showYearSelectLayout(mYear);
                    mTvMonthDay.setText(String.valueOf(mYear));
                }
            }
        });

    }


}
