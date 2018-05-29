package com.ascend.wangfeng.wifimanage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 * 时间规划
 */

public class Plan implements Serializable,MultiItemEntity{

    private static final long serialVersionUID = -1314258825409184663L;

    private Long id;
    private Long dId;
    private Long bId;
    private int type;// 规划类型,即每周哪几天执行
    private Long starttime;
    private Long endtime;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Long getStarttime() {
        return this.starttime;
    }
    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }
    public Long getEndtime() {
        return this.endtime;
    }
    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    @Override
    public int getItemType() {
        return 0;
    }
    final String[] items = {"每日", "工作日(周一至周五)", "假日(周末)"};
    public String getTypeStr(){
        return items[getType()];
    }
}
