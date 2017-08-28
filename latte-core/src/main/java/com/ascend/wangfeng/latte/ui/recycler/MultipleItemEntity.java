package com.ascend.wangfeng.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class MultipleItemEntity implements MultiItemEntity{
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUEUE =new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FIELDS =new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS,ITEM_QUEUE);

    public MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }
    public final <T> T getField(Object key){
        return (T)FIELDS_REFERENCE.get().get(key);
    }
    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }
    public final MultipleItemEntity setField(Object key,Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }
    public static final class Builder{
        private static final LinkedHashMap<Object,Object> FIELDS =new LinkedHashMap<>();

        public Builder() {
            FIELDS.clear();
        }
        public final Builder setItemType(int type){
            FIELDS.put(MultipleFields.ITEM_TYPE,type);
            return this;
        }
        public final Builder setField(Object key,Object value){
            FIELDS.put(key,value);
            return this;
        }
        public final Builder setFields(LinkedHashMap<Object,Object> fields){
            FIELDS.putAll(fields);
            return this;
        }
        public MultipleItemEntity build(){
            return new MultipleItemEntity(FIELDS);
        }
    }
}
