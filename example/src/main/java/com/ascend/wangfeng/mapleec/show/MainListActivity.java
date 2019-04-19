package com.ascend.wangfeng.mapleec.show;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.ascend.wangfeng.mapleec.BannerActivity;

import com.ascend.wangfeng.mapleec.LoaderActivity;
import com.ascend.wangfeng.mapleec.R;


/**
 * @author maple on 2019/4/19 15:27.
 * @version v1.0
 * @see 1040441325@qq.com
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import qiu.niorgai.StatusBarCompat;

public class MainListActivity extends AppCompatActivity {
    public ArrayList<ActivityItem> mItems;
    private ActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delegate_main);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        StatusBarCompat.translucentStatusBar(this, true);
        RecyclerView rv = findViewById(R.id.rv);
        mItems = getItems();
        mAdapter = new ActivityAdapter(mItems);
        mAdapter.setListener(item -> startActivity(new Intent(MainListActivity.this, item.activity)));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    public ArrayList<ActivityItem> getItems() {
        mItems = new ArrayList<>();
        mItems.add(new ActivityItem(BannerActivity.class));
        mItems.add(new ActivityItem(LoaderActivity.class));
        return mItems;

    }
}