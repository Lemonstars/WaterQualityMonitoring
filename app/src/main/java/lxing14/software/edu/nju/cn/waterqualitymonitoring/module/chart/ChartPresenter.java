package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import java.util.ArrayList;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterTypeEnum;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/28
 * @time : 下午9:13
 */

public class ChartPresenter implements ChartContract.Presenter {

    private ChartContract.View mView;

    private int mType;
    private boolean mIsRealTime;
    private String mStartTime;
    private String mEndTime;
    private ArrayList<String> mDateList;
    private ArrayList<Float> mDataList;

    public ChartPresenter(ChartContract.View mView, int type, boolean isRealTime, String startTime,
                          String endTime, ArrayList<String> dateList, ArrayList<Float> dataList) {
        this.mView = mView;
        this.mType = type;
        this.mIsRealTime = isRealTime;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mDateList = dateList;
        this.mDataList = dataList;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadChartData() {

        String chartName;
        String type;
        switch (mType){
            case WaterTypeEnum.WATER_LEVEL:
                type = "水位";
                break;
            case WaterTypeEnum.WATER_QUALITY:
                type = "水质";
                break;
            case WaterTypeEnum.WATER_FLOW:
                type = "流量";
                break;
            case WaterTypeEnum.WATER_FLOATING:
                type = "漂浮物";
                break;
            case WaterTypeEnum.WATER_BOAT:
                type = "无人船";
                break;
            default:
                type = "";
        }

        if(mIsRealTime){
            chartName = type+"实时图";
        }else {
            chartName = mStartTime+"至"+mEndTime+type+"图";
        }

        mView.showChartName(chartName);
        mView.showChart(mDateList, mDataList);
        mView.showChartUnit("m");
    }
}
