package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午7:18
 */

public interface WaterQualityContract {

    interface View extends BaseView<WaterQualityContract.Presenter>{

        /**
         * 显示站点位置
         * @param markerOptionsList
         */
        void showStationLocation(ArrayList<MarkerOptions> markerOptionsList);

        /**
         * 显示图表的单位
         * @param unit
         */
        void showChartUnit(String unit);

        /**
         * 配置图表marker显示的条目和单位
         * @param entry
         * @param unit
         */
        void configChartMarkerView(String entry, String unit);

        /**
         * 显示水质图表
         * @param dataList
         * @param dateList
         */
        void showWaterQualityChart(List<String> dateList, List<Float> dataList);

        /**
         * 显示当前水质数据
         * @param waterQualityList
         */
        void showCurrentWaterQualityInfo(List<Double> waterQualityList);
    }

    interface Presenter extends BasePresenter{

        /**
         * 跳转至ChartActivity
         */
        void jumpToChartActivity();

        /**
         * 根据起止时间获取当前图表数据
         * @param startTime
         * @param endTime
         */
        void loadChartDataByDate(String startTime, String endTime);

        /**
         * 获取当前水质数据
         */
        void loadCurrentWaterQualityInfo();

        /**
         * 加载所有站点信息
         */
        void loadAllStationInfo();

    }

}
