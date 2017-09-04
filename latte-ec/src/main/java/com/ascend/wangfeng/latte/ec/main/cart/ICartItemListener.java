package com.ascend.wangfeng.latte.ec.main.cart;

/**
 * Created by fengye on 2017/9/1.
 * email 1040441325@qq.com
 */

public interface ICartItemListener {
    /**
     *
     * @param itemToatalPrice 单件总价
     */
    void onItemClick(double itemToatalPrice);

    /**
     *
     * @param sum 所有的总价
     */
    void onSum(double sum);
}
