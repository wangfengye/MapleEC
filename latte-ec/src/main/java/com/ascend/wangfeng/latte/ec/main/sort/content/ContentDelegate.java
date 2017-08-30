package com.ascend.wangfeng.latte.ec.main.sort.content;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class ContentDelegate extends LatteDelegate {
    private static final String ARG_CONTENT_ID ="CONTENT_ID";
    private int mContenId =-1;
    @BindView(R2.id.content_list)
    RecyclerView mRecyclerView;
    private List<SectionBean> mData;

    public static ContentDelegate newInstance(int contenId){
        final Bundle args =new Bundle();
        args.putInt(ARG_CONTENT_ID,contenId);
        return new ContentDelegate();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args =getArguments();
        if (args!=null){
            mContenId = args.getInt(ARG_CONTENT_ID);
        }
    }
    private void initData(){
        String url ="sortcontent?contentId="+mContenId;
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public Context getShowContext() {
                        return getContext();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                         mData =  new SectionDataConverter().convert(s);
                        final SectionAdapter adapter = new SectionAdapter(R.layout.item_section_content,
                                R.layout.item_section_header,mData);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }
}
