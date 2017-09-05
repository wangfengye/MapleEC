package com.ascend.wangfeng.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by fengye on 2017/9/5.
 * email 1040441325@qq.com
 */

public class AddressDelegate extends LatteDelegate {
    @BindView(R2.id.icon_address_add)
    IconTextView mIconAddressAdd;
    @BindView(R2.id.rv_address)
    RecyclerView mRvAddress;


    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        RxRestClient.builder()
                .url("address")
                .build()
                .get()
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        LinearLayoutManager manager =new LinearLayoutManager(getContext());
                        mRvAddress.setLayoutManager(manager);
                        AddressAdapter adapter = new AddressAdapter(
                                new AddressDataConverter().setJsonData(s).convert());
                        mRvAddress.setAdapter(adapter);

                    }
                });
    }


}
