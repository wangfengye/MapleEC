package com.ascend.wangfeng.locationby4g.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 */

public class ArrayUtil {
    /**
     * 集合深拷贝
     * @param list 源集合
     * @param start  起始index
     * @param end  末尾index+1
     * @return
     */
    public static List copy(List list,int start,int end){
        List copy = new ArrayList();
        for (int i = start; i < end; i++) {
            copy.add(list.get(i));
        }
        return copy;
    }
    public static List copy(List list){
       return copy(list,0,list.size());
    }
}
