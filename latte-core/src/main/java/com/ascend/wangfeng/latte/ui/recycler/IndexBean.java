package com.ascend.wangfeng.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class IndexBean implements MultiItemEntity{
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int TEXT_IMAGE = 3;
    public static final int BANNER = 4;

    private int id;
    private String imageUrl;
    private String text;
    private int spanSize;
    private List<String> banners;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public int getType() {
        int type = 0;
        if (imageUrl == null && text != null) {
            type=TEXT;
        }else if (imageUrl!=null&&text==null){
            type=IMAGE;
        }else if (imageUrl!=null){
            type=TEXT_IMAGE;
        }else if (banners!=null){
            type =BANNER;
        }
        return type;
    }

    @Override
    public String toString() {
        return "IndexBean{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", text='" + text + '\'' +
                ", spanSize=" + spanSize +
                ", banners=" + banners +
                '}';
    }

    @Override
    public int getItemType() {
        int type = 0;
        if (imageUrl == null && text != null) {
            type=TEXT;
        }else if (imageUrl!=null&&text==null){
            type=IMAGE;
        }else if (imageUrl!=null){
            type=TEXT_IMAGE;
        }else if (banners!=null){
            type =BANNER;
        }
        return type;
    }
}
