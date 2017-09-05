package com.ascend.wangfeng.latte.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by fengye on 2017/9/5.
 * email 1040441325@qq.com
 */

public class AddressAdapter extends MultipleRecyclerAdapter{
    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case ItemType.ITEM_ADDRESS:
                final int position =helper.getAdapterPosition();
                final String name = item.getField(MultipleFields.NAME);
                final String phone = item.getField(MultipleFields.PHONE);
                final String address = item.getField(MultipleFields.ADDRESS);
                final int id = item.getField(MultipleFields.ID);
                final boolean isDefault = item.getField(MultipleFields.TAG);
                final AppCompatTextView nameText =helper.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText =helper.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText =helper.getView(R.id.tv_address_address);
                final AppCompatTextView deleteText =helper.getView(R.id.tv_address_delete);
                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //真实情况应该执行删除api,重新获取数据
                        remove(position);
                        notifyItemRangeChanged(position,getItemCount());
                    }
                });

                break;
            default:
                break;
        }
    }
}
