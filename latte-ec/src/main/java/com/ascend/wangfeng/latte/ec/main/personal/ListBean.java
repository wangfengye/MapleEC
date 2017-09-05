package com.ascend.wangfeng.latte.ec.main.personal;

import android.widget.CompoundButton;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class ListBean implements MultiItemEntity {
    public static final int TYPE_NORMAL =0;
    public static final int TYPE_AVATAR =1;
    public static final int TYPE_SWITCH =2;
    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText;
    private String mValue;
    private int mId = 0;
    private LatteDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public ListBean(int itemType, String imageUrl, String text, String value, int id, LatteDelegate delegate, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mItemType = itemType;
        mImageUrl = imageUrl;
        mText = text;
        mValue = value;
        mId = id;
        mDelegate = delegate;
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText == null)return "";
        return mText;
    }

    public String getValue() {
        if (mValue == null)return "";
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int mItemType = 0;
        private String mImageUrl = null;
        private String mText;
        private String mValue;
        private int mId = 0;
        private LatteDelegate mDelegate = null;
        private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

        public Builder setItemType(int itemType) {
            mItemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            mText = text;
            return this;
        }

        public Builder setValue(String value) {
            mValue = value;
            return this;
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setDelegate(LatteDelegate delegate) {
            mDelegate = delegate;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            mOnCheckedChangeListener = listener;
            return this;
        }

        public ListBean build() {
            return new ListBean(mItemType, mImageUrl, mText, mValue, mId, mDelegate, mOnCheckedChangeListener);
        }
    }

}
