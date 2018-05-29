package com.ascend.wangfeng.wifimanage.bean.vo;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 */

public class PlanVo extends Plan implements MultiItemEntity{
    private static final long serialVersionUID = -6961579058656610140L;

    @Override
    public int getItemType() {
        return 0;
    }
    public static PlanVo get(Plan plan){
        String json = JSON.toJSONString(plan);
        PlanVo personVo = JSON.parseObject(json,PlanVo.class);
        return personVo;
    }

}
