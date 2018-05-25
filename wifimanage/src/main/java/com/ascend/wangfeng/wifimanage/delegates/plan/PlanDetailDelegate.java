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
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

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
      /*  PlanDao dao = ((MainApp) (getActivity().getApplication())).getDaoSession().getPlanDao();
        if (mPlan.getId() != null && mPlan.getId() != 0) {
            dao.delete(mPlan);
        }
        pop();*/
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
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mIcEdit.setVisibility(View.VISIBLE);
        mIcEdit.setText("{fa-save}");
        mIcEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /*     PlanDao dao = ((MainApp) (getActivity().getApplication())).getDaoSession().getPlanDao();
                if (mPlan.getId() != null && mPlan.getId() != 0) {
                    dao.update(mPlan);
                } else {
                    dao.insert(mPlan);
                }
                pop();*/
            }
        });
        initData();
        initRepeat();
        initStart();
        initEnd();
    }


    private void initData() {
        mPlan = (Plan) getArguments().getSerializable(PLAN);
        if (mPlan == null) {
            mPlan = new Plan();
            mPlan.setType(0);
            mPlan.setStarttime(getTime(9, 0));
            mPlan.setEndtime(getTime(18, 0));
        } else {
            // 加载数据
            mTvRepeatContent.setText(items[mPlan.getType()]);
            mTvStartContent.setText(time2Str(mPlan.getStarttime()));
            mTvEndContent.setText(time2Str(mPlan.getEndtime()));
        }
    }

    // 重复规划
    private void initRepeat() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                mTvRepeatContent.setText(items[i]);
                //存储结果
                anInterface.dismiss();
                mPlan.setType(i);
            }
        });
        mRlReapeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });
    }

    public void initStart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                // 更新时间
                mPlan.setStarttime(getTime(mStartHour, mStartMinute));
                mTvStartContent.setText(String.format("%02d : %02d", mStartHour, mStartMinute));
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getContext(), R.layout.dialog_time, null);
        final TimePicker picker = (TimePicker) dialogView.findViewById(R.id.dp);
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
        mRlEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }


}
