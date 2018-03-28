package com.ascend.wangfeng.locationby4g.delegates.equipment;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.locationby4g.Config;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.services.bean.CellSysAck;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;

import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 * 传递对象的Recyclerview
 */

public class EquipmentAdapter extends BaseMultiItemQuickAdapter<EquipmentEntry, MultipleViewHolder> {

    public EquipmentAdapter(List<EquipmentEntry> data) {
        super(data);
        addItemType(EquipmentEntry.DIALOG, R.layout.item_multiple_dialog);
        addItemType(EquipmentEntry.MULTIPLE_CHOICE, R.layout.item_multiple_choices);
        addItemType(EquipmentEntry.LIST, R.layout.item_multiple_list);
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final EquipmentEntry item) {
        helper.setText(R.id.tv_title, item.getTitle());
        switch (item.getItemType()) {
            case EquipmentEntry.DIALOG:
                helper.setOnClickListener(R.id.rl_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.getDialog().show();
                    }
                });
                break;
            case EquipmentEntry.MULTIPLE_CHOICE:
                helper.setText(R.id.rb_1, item.getChoices().get(0));
                helper.setText(R.id.rb_2, item.getChoices().get(1));
                helper.setText(R.id.rb_3, item.getChoices().get(2));
                ((RadioGroup) helper.getView(R.id.rg_choices)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        item.getCallback().onClickListener(checkedId);
                    }
                });
                break;
            case EquipmentEntry.LIST:
                int i = 0;
                LinearLayout ll = helper.getView(R.id.ll_container);
                ll.removeAllViews();
                for (Map.Entry<String, Boolean> entry : item.getListMap().entrySet()) {
                    LayoutInflater.from(ll.getContext()).inflate(com.ascend.wangfeng.latte.R.layout.bottom_item_icon_text_layout, ll);
                    final RelativeLayout rl = (RelativeLayout) ll.getChildAt(i);
                    final int finalI = i;
                    rl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.getCallback().onClickListener(0);
                            buildDialog(finalI, rl.getContext());
                        }
                    });
                    final IconTextView iTv = (IconTextView) rl.getChildAt(0);
                    final TextView tv = (TextView) rl.getChildAt(1);
                    iTv.setText("{fa-circle}");
                    // 三元表达式为了让联通,电信复用
                    CellSysAck ack = Config.getInstance().getAcks().get((i == 2 ? 1 : i));
                    Socket socket = ack.getSocket();
                    if (socket != null && socket.isConnected()) {
                        if (ack.getState() == CellSysAck.STATE_SYNC_ACTIVE || ack.getState() == CellSysAck.STATE_UNSYNC_ACTIVE) {
                            iTv.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.success));
                        } else {
                            iTv.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.danger));
                        }
                    } else {
                        iTv.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.danger));
                    }
                    tv.setText(entry.getKey());
                    i++;
                }
                break;
        }

    }

    /**
     * 展示三个Socket信息
     *
     * @param i
     */
    private void buildDialog(int i, Context context) {
        // 特殊处理,电信和联通共用同一socket
        if (i == 2) {
            i = 1;
            Config.getInstance().getAcks().get(i).setTitle("中国联通");
        } else if (i == 1) {
            Config.getInstance().getAcks().get(i).setTitle("中国电信");
        }
        String[] items = Config.getInstance().getAcks().get(i).getArrayStr();
        new AlertDialog.Builder(context)
                .setTitle("详情")
                .setItems(items, null)
                .show();
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
