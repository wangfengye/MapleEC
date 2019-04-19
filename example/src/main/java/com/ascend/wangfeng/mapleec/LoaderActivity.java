package com.ascend.wangfeng.mapleec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;
import com.wang.avi.indicators.LineScalePulseOutRapidIndicator;

public class LoaderActivity extends AppCompatActivity {
    public static final String TITLE = "Loading使用";
    private String mType = LoaderStyle.BallClipRotatePulseIndicator.name();
    private boolean mHideBack;
    private static final String[] TYPES = new String[]{LoaderStyle.BallClipRotatePulseIndicator.name(), LoaderStyle.CubeTransitionIndicator.name(), LoaderStyle.LineScalePulseOutRapidIndicator.name()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        SwitchCompat sw = findViewById(R.id.sw_back);
        sw.setOnCheckedChangeListener((view, checked) -> mHideBack = checked);
        RadioGroup group = findViewById(R.id.rb_group);
        group.setOnCheckedChangeListener((rbGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton:
                    mType = TYPES[0];
                    break;
                case R.id.radioButton2:
                    mType = TYPES[1];
                    break;
                default:
                    mType = TYPES[2];
                    break;
            }
        });
        findViewById(R.id.btn).setOnClickListener(v -> LatteLoader.showLoading(this, mType, mHideBack));
    }
}
