package com.ascend.wangfeng.latte.ec.main.sort;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.main.sort.content.ContentDelegate;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.ui.recycler.MultipleRecyclerAdapter;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {
    private int mPosition;//记录当前焦点
    private final SortDelegate DELEGATE;

    public SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = item.getField(MultipleFields.TEXT);
                final boolean isClicked = item.getField(MultipleFields.TAG);
                final TextView name = helper.getView(R.id.tv_vertical_item_name);
                final View line = helper.getView(R.id.view_line);
                name.setText(text);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (helper.getAdapterPosition()!=mPosition){
                            getData().get(helper.getAdapterPosition()).setField(MultipleFields.TAG,true);
                            getData().get(mPosition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(helper.getAdapterPosition());
                            notifyItemChanged(mPosition);
                            showContent((Integer) getData().get(helper.getAdapterPosition()).getField(MultipleFields.ID));
                        }
                    }
                });
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    helper.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    mPosition=helper.getAdapterPosition();
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    helper.itemView.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }
    }
    private void showContent(int contentId){
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);

    }
    private void switchContent(ContentDelegate delegate){
     /*   final LatteDelegate contentDelegate =DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate!=null){
            contentDelegate.getDelegate().replaceFragment(delegate,false);
        }*/
    }
}
