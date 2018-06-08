package com.ascend.wangfeng.wifimanage.delegates.index;

import android.graphics.Color;
import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.OnAdapterListener;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by fengye on 2018/6/5.
 * email 1040441325@qq.com
 */

public class IndexPersonAdapter extends BaseMultiItemQuickAdapter<Person, MultipleViewHolder> {
    private int mColor = Color.parseColor("#10D7C6");
    private OnAdapterListener<Person> mListener;
    public IndexPersonAdapter(List<Person> data, int color) {
        super(data);
        mColor =color;
        addItemType(0, R.layout.item_person_main);
    }
    public void setListener(OnAdapterListener<Person> listener){
        mListener = listener;
    }
    public void setPIds(LinkedHashSet<Long> pIds){
        getData().clear();
        for (Long i:pIds) {
            Person p = new Person();
            p.setPid(i);
            getData().add(p);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        // 最多显示6个
        return getData().size()>5?5:getData().size();
    }

    @Override
    protected void convert(MultipleViewHolder helper,Person item) {
        CircleImageView cImg = helper.getView(R.id.cimg_icon);
        cImg.setBg(mColor);
        cImg.setSrcType(CircleImageView.TYPE_NORMAL);
        Client.getInstance().getPersonById(item.getPid())
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onSuccess(Response<Person> response) {
                        cImg.setOnClickListener(view -> {
                            if (mListener!=null)mListener.onclick(response.getData());
                        });
                        cImg.setImage(Icon.getImgUrl(response.getData().getPimage()));
                        helper.setText(R.id.tv_name,response.getData().getPname());
                    }
                });
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}