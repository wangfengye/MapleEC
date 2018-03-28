package com.ascend.wangfeng.locationby4g.delegates.imsi;

import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;
import com.ascend.wangfeng.locationby4g.util.TimeUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 */

public class ImsiAdapter extends BaseMultiItemQuickAdapter<CellMeaureAckEntry,MultipleViewHolder> {
    private LatteDelegate mDelegate;
    public ImsiAdapter(List<CellMeaureAckEntry> data, LatteDelegate delegate) {
        super(data);
        mDelegate = delegate;
        addItemType(0, R.layout.item_multiple_imsi);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final CellMeaureAckEntry item) {
        helper.setText(R.id.tv_imsi,item.getImsi());
        helper.setText(R.id.tv_time, TimeUtil.format(item.getTimestamp()));
        helper.setText(R.id.tv_operator,getImsiOperator(item.getImsi()));
        helper.setText(R.id.tv_count,(item.getFieldIntensitys().size()+1) + "次");
        helper.setText(R.id.tv_dbm,item.getFieldIntensity() + "dBm");
        helper.setText(R.id.tv_error_range,getErrorRange(item));
        helper.setOnClickListener(R.id.cl_imsi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.getParentDelegate().start(ImsiLineDelegate.newInstance(item.getImsi()));
            }
        });
        if (item.isLocation()){
            helper.getView(R.id.iv_location).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.iv_location).setVisibility(View.INVISIBLE);
        }
    }

    /**
     *  获取与最近一次采集元素的误差
     * @param item
     * @return
     */
    private String getErrorRange(CellMeaureAck item) {
        if (item.getFieldIntensitys().size() == 0) return "0";
        int last = item.getFieldIntensitys().get(item.getFieldIntensitys().size()-1);

        return formatRange(item.getFieldIntensity() - last);
    }

    private String getImsiOperator(String imsi) {
        String mnc = imsi.substring(3, 5);
        String operator = null;
        if (equals(mnc, new String[]{"00", "02", "04", "07"})) {
            operator = "中国移动";
        } else if (equals(mnc, new String[]{"01", "06", "09"})) {
            operator = "中国联通";
        } else if (equals(mnc, new String[]{"03", "05"})) {
            operator = "中国电信";
        } else if (equals(mnc, new String[]{"20"})) {
            operator = "中国铁通";
        } else {
            operator = "未知";
        }
        return operator;
    }
    private boolean equals(String target, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (target.equals(strings[i])) return true;
        }
        return false;
    }

    /**
     *  数值格式化为存在正负号的
     * @param num
     * @return
     */
    private String formatRange(int num){
        if (num > 0){
            return "+" + num;
        } else {
            return  String.valueOf(num);
        }
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
