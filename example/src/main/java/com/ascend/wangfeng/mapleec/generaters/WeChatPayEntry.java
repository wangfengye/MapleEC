package com.ascend.wangfeng.mapleec.generaters;

import com.ascend.wangfeng.latte.wechat.template.WXPayEntryTemplate;
import com.example.annotations.PayEntryGenerator;

/**
 * Created by fengye on 2017/8/23.
 * email 1040441325@qq.com
 */
@PayEntryGenerator(
        packageName = "com.ascend.wangfeng.mapleec",
        entryTemplete = WXPayEntryTemplate.class
)
public class WeChatPayEntry {
}
