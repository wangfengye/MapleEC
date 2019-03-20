package com.maple.webview.fun1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.nukc.stateview.StateView;
import com.maple.webview.R;
import com.maple.webview.widget.WebDetail;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mRv;
    private ArrayList<String> mComments;
    private StateView mStateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_main);
        QbSdk.initX5Environment(getApplicationContext(), null);
        mStateView = StateView.inject(this);
        mStateView.showLoading();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRv = findViewById(R.id.rv);
        mComments = new ArrayList<>();
        mComments.add("a");
        mComments.add("abdsa");
        mComments.add("asdfsdfwe");
        CommentAdapter adapter = new CommentAdapter(R.layout.item_comment, mComments);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(adapter);
        WebDetail detail = new WebDetail(mRv.getContext());
        detail.setListener(new WebDetail.LoadListener() {
            @Override
            public void loaded() {
                mStateView.showContent();
            }
        });
        adapter.addHeaderView(detail);
        adapter.setHeaderAndEmpty(true);
    }
}
