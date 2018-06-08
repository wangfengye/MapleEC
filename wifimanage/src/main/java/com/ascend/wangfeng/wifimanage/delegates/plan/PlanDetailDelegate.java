package com.ascend.wangfeng.wifimanage.delegates.plan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.getHour;
import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.getMinute;
import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.getTime;
import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.time2Str;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 */

public class PlanDetailDelegate extends LatteDelegate {
    public static final String TAG = PlanDetailDelegate.class.getSimpleName();
    public static final String PLAN = "plan";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_repeat_title)
    TextView mTvRepeatTitle;
    @BindView(R.id.tv_repeat_content)
    TextView mTvRepeatContent;
    @BindView(R.id.rl_reapeat)
    RelativeLayout mRlReapeat;
    @BindView(R.id.tv_start_title)
    TextView mTvStartTitle;
    @BindView(R.id.tv_start_content)
    TextView mTvStartContent;
    @BindView(R.id.rl_start)
    RelativeLayout mRlStart;
    @BindView(R.id.tv_end_title)
    TextView mTvEndTitle;
    @BindView(R.id.tv_end_content)
    TextView mTvEndContent;
    @BindView(R.id.rl_end)
    RelativeLayout mRlEnd;

    Plan mPlan;
    private int mStartHour;
    private int mStartMinute;
    private int mEndHour;
    private int mEndMinute;
    final String[] items = {"每日", "工作日(周一至周五)", "假日(周末)"};

    @OnClick(R.id.btn_delete)
    void clickBtnDelete() {
        // 删除计划
        add(Client.getInstance().delPlan(mPlan.getPid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new MyObserver<Response<String>>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MainApp.toast(R.string.delete_success);
                        pop();
                    }
                }));
    }

    public static PlanDetailDelegate newInstance(Bundle args) {
        PlanDetailDelegate fragment = new PlanDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_plan_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("计划编辑");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(view -> pop());
        mIcEdit.setVisibility(View.VISIBLE);
        mIcEdit.setText("{fa-save}");
        mIcEdit.setOnClickListener(view -> {
            // 保存计划
            if (mPlan.getPid() != null && mPlan.getPid() != 0) {
                // update
                add(Client.getInstance().updatePlan(mPlan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new MyObserver<Response<Plan>>() {
                            @Override
                            public void onSuccess(Response<Plan> response) {
                                MainApp.toast(R.string.update_success);
                                pop();
                            }
                        }));
            } else {
                // add
                add(Client.getInstance().addPlan(mPlan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new MyObserver<Response<Plan>>() {
                            @Override
                            public void onSuccess(Response<Plan> response) {
                                MainApp.toast(R.string.add_success);
                                pop();
                            }
                        }));
            }
        });
        // 特殊情况,由于数据直接使用bundle传输,所以直接在onBindView上初始化数据,减少重复设置点击事件
        initData();
        initRepeat();
        initStart();
        initEnd();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    private void initData() {
        mPlan = (Plan) getArguments().getSerializable(PLAN);
        if (mPlan == null) {
            mPlan = new Plan();
            mPlan.setPtype(0);
            mPlan.setStarttime(getTime(9, 0));
            mPlan.setEndtime(getTime(18, 0));
        } else {
            // 加载数据
            mTvRepeatContent.setText(items[mPlan.getPtype()]);
            mTvStartContent.setText(time2Str(mPlan.getStarttime()));
            mTvEndContent.setText(time2Str(mPlan.getEndtime()));
        }
    }

    // 重复规划
    private void initRepeat() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, (DialogInterface anInterface, int i)-> {
                mTvRepeatContent.setText(items[i]);
                //存储结果
                anInterface.dismiss();
                mPlan.setPtype(i);
        });
        mRlReapeat.setOnClickListener(view-> builder.show());
    }

    public void initStart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("设置",(DialogInterface anInterface, int i)-> {
                // 更新时间
                mPlan.setStarttime(getTime(mStartHour, mStartMinute));
                mTvStartContent.setText(String.format("%02d : %02d", mStartHour, mStartMinute));
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getContext(), R.layout.dialog_time, null);
        final TimePicker picker =  dialogView.findViewById(R.id.dp);
        dialog.setView(dialogView);
        picker.setIs24HourView(true);
        picker.setHour(getHour(mPlan.getStarttime()));
        picker.setMinute(getMinute(mPlan.getStarttime()));
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker picker, int i, int i1) {
                mStartHour = i;
                mStartMinute = i1;
            }
        });
        mRlStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    private void initEnd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                // 更新时间
                mPlan.setEndtime(getTime(mEndHour, mEndMinute));
                mTvEndContent.setText(String.format("%02d : %02d", mEndHour, mEndMinute));
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getContext(), R.layout.dialog_time, null);
        final TimePicker picker = (TimePicker) dialogView.findViewById(R.id.dp);
        dialog.setView(dialogView);
        picker.setIs24HourView(true);
        picker.setHour(getHour(mPlan.getEndtime()));
        picker.setMinute(getMinute(mPlan.getEndtime()));
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker picker, int i, int i1) {
                mEndHour = i;
                mEndMinute = i1;
            }
        });
        mRlEnd.setOnClickListener(view -> dialog.show());
    }


}
