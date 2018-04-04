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

    private int flowId;
    private int stnId;
    private double avgSpeed;
    private double riverArea;
    private double avgFlow;
    private String collectionTime;
    private String state;
    private double dayFlow;
    private double weekFlow;
    private double monthFlow;
    private double yearFlow;
    private double totalFlow;

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getStnId() {
        return stnId;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getRiverArea() {
        return riverArea;
    }

    public void setRiverArea(double riverArea) {
        this.riverArea = riverArea;
    }

    public double getAvgFlow() {
        return avgFlow;
    }

    public void setAvgFlow(double avgFlow) {
        this.avgFlow = avgFlow;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getDayFlow() {
        return dayFlow;
    }

    public void setDayFlow(double dayFlow) {
        this.dayFlow = dayFlow;
    }

    public double getWeekFlow() {
        return weekFlow;
    }

    public void setWeekFlow(double weekFlow) {
        this.weekFlow = weekFlow;
    }

    public double getMonthFlow() {
        return monthFlow;
    }

    public void setMonthFlow(double monthFlow) {
        this.monthFlow = monthFlow;
    }

    public double getYearFlow() {
        return yearFlow;
    }

    public void setYearFlow(double yearFlow) {
        this.yearFlow = yearFlow;
    }

    public double getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(double totalFlow) {
        this.totalFlow = totalFlow;
    }
}
