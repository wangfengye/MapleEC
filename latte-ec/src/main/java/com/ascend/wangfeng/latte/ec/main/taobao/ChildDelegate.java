package com.ascend.wangfeng.latte.ec.main.taobao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maple on 2019/2/20 16:45.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class ChildDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_child;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RecyclerView mRv = rootView.findViewById(R.id.rv);
        SmartRefreshLayout sr = rootView.findViewById(R.id.sr);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(1);
        }
        Adapter adapter = new Adapter(list);
        mRv.setAdapter(adapter);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRv.setNestedScrollingEnabled(false);
        sr.setOnRefreshListener(refreshLayout->
                sr.finishRefresh(2000));
        sr.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            for (int i = 0; i < 10; i++) {
                list.add(1);
            }
            adapter.notifyDataSetChanged();
        });
    }
    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
        private List<Integer> mList;
        public Adapter(List<Integer> list) {
            this.mList =list;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taobao,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTv.setText("第 " + String.valueOf(position)+" 个item");
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTv;
            public ViewHolder(View itemView) {
                super(itemView);
                mTv = itemView.findViewById(R.id.tv);
            }
        }
    }
}
