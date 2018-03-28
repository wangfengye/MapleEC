package com.ascend.wangfeng.locationby4g.services.bean;

import java.util.ArrayList;

/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 * 指令拼接
 */

public class CellCmd {
    private ArrayList<String> mStrings;

    private CellCmd() {
        mStrings = new ArrayList<>();
    }

    /**
     * 查询设备运行状态
     */
    public static String setCellSynReq() {
        return CellCmd.build(12).toString();
    }

    /**
     * 开启侦测 命令
     *
     * @param type     rfo:5,rf1:19
     * @param idensity 功率
     */
    public static String setCellPwrAdjCmd(int type, int idensity) {
        return CellCmd.build(type).addBodyInt(idensity).toString();
    }

    /**
     * 复位
     */
    public static String setCellRstCmd() {
        return CellCmd.build(6).toString();
    }

    /**
     * 定位目标
     *
     * @param imsi
     * @param type 2:定位 1:释放
     */
    public static String setCellNumListStopTCmd(String imsi, int type) {
        return CellCmd.build(47).addBodyInt(1).addImsi(imsi).addBodyInt(type).toString();
    }
    public static CellCmd build(int type){
        CellCmd cmd = new CellCmd();
        // 头信息,类型
        cmd.addInt(type,4);
        // 头信息,长度
        cmd.addInt(8,4);
        return  cmd;
    }
    public  CellCmd addBodyInt(Integer data){
        return addBodyInt(data,1);
    }
    public CellCmd addBodyInt(Integer data,int length){
        addInt(data,length);
        updateLength();
        return this;
    }
    public CellCmd addImsi(String imsi){
        for (int i = 0; i < imsi.length(); i++) {
            mStrings.add("0" + imsi.charAt(i));
        }
        updateLength();
        return this;
    }

    /**
     * 添加数值型数据
     * @param data
     * @param length
     */
    private void addInt(Integer data,int length){
        String dataStr = Integer.toHexString(data).toString().toUpperCase();
        StringBuilder dataBuilder = new StringBuilder(dataStr);
        for (int i = 0; i < length; i++) {
            if (dataBuilder.length()>=2){
                mStrings.add(dataBuilder.substring(dataBuilder.length()-2));
                dataBuilder.deleteCharAt(dataBuilder.length()-1);
                dataBuilder.deleteCharAt(dataBuilder.length()-1);
            }else if (dataBuilder.length()>=1){
                mStrings.add("0" + dataBuilder.substring(dataBuilder.length()-1));
                dataBuilder.deleteCharAt(dataBuilder.length()-1);
            }else {
                mStrings.add("00");
            }
        }
    }

    /**
     * 更新头信息的长度
     */
    private void updateLength(){
        int length = mStrings.size();
        StringBuilder builder = new StringBuilder(Integer.toHexString(length).toUpperCase().toString());
        for (int i = 0; i <= builder.length()/2; i++) {
            if (builder.length()>1){
                mStrings.set(4+i,builder.substring(builder.length()-2));
                builder.deleteCharAt(builder.length()-1);
                builder.deleteCharAt(builder.length()-1);
            }else if (builder.length()>0){
                mStrings.set(4+i,"0"+builder.substring(builder.length()-1));
                builder.deleteCharAt(builder.length()-1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mStrings.size(); i++) {
            builder.append(mStrings.get(i));
        }
        return builder.toString();
    }
}
