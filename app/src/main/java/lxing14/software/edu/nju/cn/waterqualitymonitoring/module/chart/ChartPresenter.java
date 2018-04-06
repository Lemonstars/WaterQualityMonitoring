package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import java.util.ArrayList;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/28
 * @time : 下午9:13
 */

public class ChartPresenter implements ChartContract.Presenter {

    private ChartContract.View mView;

    private String mName;
    private String mStartTime;
    private String mEndTime;
    private ArrayList<String> mDateList;
    private ArrayList<Float> mDataList;

    public ChartPresenter(ChartContract.View mView,String name, String startTime,
                          String endTime, ArrayList<String> dateList, ArrayList<Float> dataList) {
        this.mView = mView;
        this.mName = name;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mDateList = dateList;
        this.mDataList = dataList;

        mView.setPresenter(this);
    }

    @Override
    public void loadChartData() {
        String chartName = mStartTime+"至"+mEndTime+mName+"图";
        mView.showChartName(chartName);
        mView.showChart(mDateList, mDataList);
        mView.showChartUnit("m");
    }
}
