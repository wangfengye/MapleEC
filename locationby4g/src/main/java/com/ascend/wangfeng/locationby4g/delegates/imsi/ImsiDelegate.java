package com.ascend.wangfeng.locationby4g.delegates.imsi;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.bottom.BottomItemDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.rxbus.RxBus;
import com.ascend.wangfeng.locationby4g.rxbus.Subscribe;
import com.ascend.wangfeng.locationby4g.services.rxbus.CMDEvent;
import com.ascend.wangfeng.locationby4g.services.rxbus.CellMeaureAckListEvent;
import com.ascend.wangfeng.locationby4g.util.ExcelUtil;
import com.ascend.wangfeng.locationby4g.util.ImsiUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class ImsiDelegate extends BottomItemDelegate {
    public static final String TAG = ImsiDelegate.class.getSimpleName();
    @BindView(R.id.rv_imsi)
    RecyclerView mRvImsi;
    @BindView(R.id.spinner_sort)
    Spinner mSpinnerSort;
    @BindView(R.id.tv_range)
    TextView mTvRange;

    @OnClick(R.id.btn_save)
    void onClickSave() {
        showSaveDialog();
    }

    /**
     * 保存文件的dialog
     */
    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_save_file, null);
        EditText etRangeMax = dialogView.findViewById(R.id.range_max);
        final EditText etRangeMin = dialogView.findViewById(R.id.range_min);
        builder.setTitle("保存记录");
        builder.setView(dialogView);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = etRangeMin.getText().toString().isEmpty()?"log":etRangeMin.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean isSuccess = ExcelUtil.writeExcel(mData, name);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess) {
                                    Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        builder.show();
    }

    @OnClick(R.id.btn_clear)
    void onClickClear(){
        RxBus.getDefault().post(new CMDEvent(CMDEvent.DATA_CLEAR));
    }

    private ArrayList<CellMeaureAckEntry> mData = new ArrayList<>();
    private ImsiAdapter mAdapter;
    private int mType;//过滤类型
    // 场强范围
    private int mRangeMax = 0;
    private int mRangeMin = 100;

    @Override
    public Object setLayout() {
        return R.layout.delegate_imsi;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initRv();
        RxBus.getDefault().register(this);
        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = position;
                if (position == 5) {
                    mTvRange.setVisibility(View.VISIBLE);
                    mAdapter.update(filterData(mData, 5, mRangeMax, mRangeMin));
                    return;
                } else {
                    mTvRange.setVisibility(View.GONE);
                    mAdapter.update(filterData(mData, position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTvRange.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showDialog();
                                        }
                                    }
        );
    }

    @Subscribe
    public void receive(CellMeaureAckListEvent event) {
        Collections.sort(event.getEntries(), new Comparator<CellMeaureAckEntry>() {
            @Override
            public int compare(CellMeaureAckEntry o1, CellMeaureAckEntry o2) {
                return (int) -(o1.getTimestamp() - o2.getTimestamp());
            }
        });
        mData = event.getEntries();
        updateView(event.getEntries());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initRv() {
        mData = new ArrayList<>();
        mAdapter = new ImsiAdapter(new ArrayList<CellMeaureAckEntry>(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvImsi.setLayoutManager(manager);
        mRvImsi.setAdapter(mAdapter);
        mRvImsi.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }

    private void updateView(final List<CellMeaureAckEntry> entries) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.update(filterData(entries, mType));
            }
        });
    }

    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public List<CellMeaureAckEntry> filterData(List<CellMeaureAckEntry> entries, int type) {
        return filterData(entries, type, mRangeMax, mRangeMin);
    }

    public List<CellMeaureAckEntry> filterData(List<CellMeaureAckEntry> entries, int type, int start, int end) {
        switch (type) {
            case 0:
                // 全部
                return entries;
            case 1:
                // 中国移动
                return getEntries(entries, "中国移动");
            case 2:
                //中国联通
                return getEntries(entries, "中国联通");
            case 3:
                //中国电信
                return getEntries(entries, "中国电信");
            case 4:
                // 其他制式
                return getEntries(entries, "未知");
            case 5:
                //场强
                return getEntries(entries, start, end);
            default:
                return entries;

        }
    }

    /**
     * 场强条件过滤
     *
     * @param entries 数据
     * @param start   最大值
     * @param end     最下值
     * @return
     */
    private List<CellMeaureAckEntry> getEntries(List<CellMeaureAckEntry> entries, int start, int end) {
        List<CellMeaureAckEntry> result = new ArrayList<>();
        for (CellMeaureAckEntry e : entries) {
            int fieldIntensity = Math.abs(e.getFieldIntensity());
            if (fieldIntensity >= start && fieldIntensity <= end) {
                result.add(e);
            }
        }
        return result;
    }

    private List<CellMeaureAckEntry> getEntries(List<CellMeaureAckEntry> entries, String operator) {
        List<CellMeaureAckEntry> result = new ArrayList<>();
        for (CellMeaureAckEntry e : entries) {
            if (ImsiUtil.getImsiOperator(e.getImsi()).equals(operator)) {
                result.add(e);
            }
        }
        return result;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_range, null);
        EditText etRangeMax = dialogView.findViewById(R.id.range_max);
        EditText etRangeMin = dialogView.findViewById(R.id.range_min);
        etRangeMax.setText(String.valueOf(mRangeMax));
        etRangeMin.setText(String.valueOf(mRangeMin));
        builder.setTitle("设置场强范围");
        builder.setMessage("场强值以绝对值计算");
        builder.setView(dialogView);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etRangeMax = dialogView.findViewById(R.id.range_max);
                EditText etRangeMin = dialogView.findViewById(R.id.range_min);
                if (etRangeMax.getText().toString().isEmpty() || etRangeMin.getText().toString().isEmpty()) {
                    Snackbar.make(mSpinnerSort, "数值不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                mRangeMax = Integer.parseInt(etRangeMax.getText().toString());
                mRangeMin = Integer.parseInt(etRangeMin.getText().toString());
                mAdapter.update(filterData(mData, 5, mRangeMax, mRangeMin));
                mTvRange.setText(mRangeMax + " ~ " + mRangeMin);
            }
        });
        builder.show();
    }
}
