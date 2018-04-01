package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午8:30
 */

public class WaterFlowVO {


    /**
     * stnId : 1
     * collectionTime : 2018-03-31 17:00:00.0
     * totalFlow : 0
     * avgFlow : 715.03
     * riverArea : 0
     * monthFlow : 0
     * avgSpeed : 0.6
     * dayFlow : 0
     * yearFlow : 0
     * state :
     * flowId : 3373
     * weekFlow : 0
     */

    private int stnId;
    private String collectionTime;
    private int totalFlow;
    private double avgFlow;
    private int riverArea;
    private int monthFlow;
    private double avgSpeed;
    private int dayFlow;
    private int yearFlow;
    private String state;
    private int flowId;
    private int weekFlow;

    public int getStnId() {
        return stnId;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public int getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(int totalFlow) {
        this.totalFlow = totalFlow;
    }

    public double getAvgFlow() {
        return avgFlow;
    }

    public void setAvgFlow(double avgFlow) {
        this.avgFlow = avgFlow;
    }

    public int getRiverArea() {
        return riverArea;
    }

    public void setRiverArea(int riverArea) {
        this.riverArea = riverArea;
    }

    public int getMonthFlow() {
        return monthFlow;
    }

    public void setMonthFlow(int monthFlow) {
        this.monthFlow = monthFlow;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getDayFlow() {
        return dayFlow;
    }

    public void setDayFlow(int dayFlow) {
        this.dayFlow = dayFlow;
    }

    public int getYearFlow() {
        return yearFlow;
    }

    public void setYearFlow(int yearFlow) {
        this.yearFlow = yearFlow;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getWeekFlow() {
        return weekFlow;
    }

    public void setWeekFlow(int weekFlow) {
        this.weekFlow = weekFlow;
    }
}
