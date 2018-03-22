package com.ascend.wangfeng.locationby4g.services.bean;

/**
 * Created by fengye on 2018/3/8.
 * email 1040441325@qq.com
 * 宏站
 */

public class Metrocell {
    // 频点
    private int frequencyPoint;
    // PCI码
    private int pci;
    // cid
    private int cid;
    // 场强
    private int intensity;
    private int tac;
    // plmnid
    private int  plmnid;
    private int sa;
    private int sp;
    // 优先级
    private int priority;
    private int hydt;
    // 异频测量门限
    private int pilotFrequencyThreshold;
    // 同频测量门限
    private int sameFrequencyThreshold;
    // 最小接入电平
    private int minElectricalLevel;
    // 重选定时器
    private int reChooseTimer;
    // 邻区个数
    private int adjacentRegionCount;

    public int getFrequencyPoint() {
        return frequencyPoint;
    }

    public void setFrequencyPoint(int frequencyPoint) {
        this.frequencyPoint = frequencyPoint;
    }

    public int getAdjacentRegionCount() {
        return adjacentRegionCount;
    }

    public void setAdjacentRegionCount(int adjacentRegionCount) {
        this.adjacentRegionCount = adjacentRegionCount;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getTac() {
        return tac;
    }

    public void setTac(int tac) {
        this.tac = tac;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getPlmnid() {
        return plmnid;
    }

    public void setPlmnid(int plmnid) {
        this.plmnid = plmnid;
    }

    public int getSa() {
        return sa;
    }

    public void setSa(int sa) {
        this.sa = sa;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getHydt() {
        return hydt;
    }

    public void setHydt(int hydt) {
        this.hydt = hydt;
    }

    public int getPilotFrequencyThreshold() {
        return pilotFrequencyThreshold;
    }

    public void setPilotFrequencyThreshold(int pilotFrequencyThreshold) {
        this.pilotFrequencyThreshold = pilotFrequencyThreshold;
    }

    public int getSameFrequencyThreshold() {
        return sameFrequencyThreshold;
    }

    public void setSameFrequencyThreshold(int sameFrequencyThreshold) {
        this.sameFrequencyThreshold = sameFrequencyThreshold;
    }

    public int getMinElectricalLevel() {
        return minElectricalLevel;
    }

    public void setMinElectricalLevel(int minElectricalLevel) {
        this.minElectricalLevel = minElectricalLevel;
    }

    public int getReChooseTimer() {
        return reChooseTimer;
    }

    public void setReChooseTimer(int reChooseTimer) {
        this.reChooseTimer = reChooseTimer;
    }

    @Override
    public String toString() {
        return "Metrocell{" +
                "frequencyPoint=" + frequencyPoint +
                ", pci=" + pci +
                ", cid=" + cid +
                ", intensity=" + intensity +
                ", tac=" + tac +
                ", plmnid=" + plmnid +
                ", sa=" + sa +
                ", sp=" + sp +
                ", priority=" + priority +
                ", hydt=" + hydt +
                ", pilotFrequencyThreshold=" + pilotFrequencyThreshold +
                ", sameFrequencyThreshold=" + sameFrequencyThreshold +
                ", minElectricalLevel=" + minElectricalLevel +
                ", reChooseTimer=" + reChooseTimer +
                '}';
    }
}
