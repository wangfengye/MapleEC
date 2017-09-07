package com.ascend.wangfeng.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ui.recycler.ItemType;
import com.ascend.wangfeng.latte.ui.recycler.MultipleFields;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fengye on 2017/9/7.
 * email 1040441325@qq.com
 */

public class ImageDelegate extends LatteDelegate{

    public static final String PICTURES = "pictures";

    @BindView(R2.id.rv_image_container)
    RecyclerView mRvImageContainer;

    public static ImageDelegate newInstance(ArrayList<String> info) {
        final Bundle args = new Bundle();
        Logger.i("test"+info);
        ImageDelegate delegate = new ImageDelegate();
        args.putStringArrayList(PICTURES, info);
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        final LinearLayoutManager manager =new LinearLayoutManager(getContext());
        mRvImageContainer.setLayoutManager(manager);
        //initImages();
        mRvImageContainer.setAdapter(initImages());
    }

    private ImageAdapter initImages() {
        final Bundle args = getArguments();
        ArrayList<String> data = args.getStringArrayList(PICTURES);
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MultipleItemEntity entity =new MultipleItemEntity.Builder()
                    .setField(MultipleFields.ITEM_TYPE,ItemType.SINGLE_BIG_IMAGE)
                    .setField(MultipleFields.IMAGE_URL,data.get(i))
                    .build();
            entities.add(entity);
        }
        final ImageAdapter adapter = new ImageAdapter(entities);
        return  adapter;
    }
}
