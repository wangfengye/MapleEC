package com.ascend.wangfeng.latte.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.ascend.wangfeng.latte.ui.recycler.MultipleItemEntity;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class SearchDelegate extends LatteDelegate {
    @BindView(R2.id.icon_top_search_back)
    IconTextView mIconTopSearchBack;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mEtSearchView;
    @BindView(R2.id.tv_top_search)
    AppCompatTextView mTvTopSearch;
    @BindView(R2.id.tb_main_page)
    Toolbar mTbMainPage;
    @BindView(R2.id.rv_search)
    RecyclerView mRvSearch;

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack(){
        getSupportDelegate().pop();
    }
    @OnClick(R2.id.tv_top_search)
    void onSearch(){
        RxRestClient.builder()
                .url("search")
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        final String searchItemText = mEtSearchView.getText().toString();
                        saveItem(searchItemText);
                        mEtSearchView.setText("");
                        //展示一些东西
                        //弹出一段话
                        String data = JSON.parseObject(s).getString("data");
                        Toast.makeText(getContext(),data , Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveItem(String text){
        if (!StringUtils.isEmpty(text)&&!StringUtils.isSpace(text)){
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEACH_HISTORY);
            List<String> history;
            if (StringUtils.isEmpty(historyStr)){
                history =new ArrayList<>();
            }else{
                history = JSON.parseObject(historyStr,ArrayList.class);
            }
            history.add(text);
            final String historyStrNew =JSON.toJSONString(history);
            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEACH_HISTORY,historyStrNew);
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSearch.setLayoutManager(manager);
        ArrayList<MultipleItemEntity> data = new SearchDataConverter().convert();
        SearchAdapter adapter = new SearchAdapter(data);
        mRvSearch.setAdapter(adapter);
        mRvSearch.addItemDecoration(getItemDecoration());
        mEtSearchView.setFocusable(true);
        mEtSearchView.setFocusableInTouchMode(true);
        mEtSearchView.requestFocus();
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(16,16)
                        .color(Color.GRAY)
                        .build();
            }
        });
        return itemDecoration;
    }


}
