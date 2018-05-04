package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/25
 * @time : 下午1:21
 */

public interface WaterLevelContract {

    interface View extends BaseView<WaterLevelContract.Presenter>{

        /**
         * 显示站点位置
         * @param markerOptionsList
         */
        void showStationLocation(ArrayList<MarkerOptions> markerOptionsList);

        /**
         * 显示水位默认图标数据
         * @param waterLevelDate
         * @param waterLevelData
         */
        void showWaterLevelChartData(List<String> waterLevelDate, List<Float> waterLevelData);

        /**
         * 显示当前水位信息
         * @param currentWaterLevel
         * @param historicalWaterLevel
         * @param pic
         * @param date
         */
        void showCurrentWaterLevelDetailInfo(String currentWaterLevel, String historicalWaterLevel,
                                             String[] pic, String[] date);

    }

    interface Presenter extends BasePresenter{

        /**
         * 处理tab点击
         * @param index
         */
        void processTab(int index);

        /**
         * 获取指定时间范围内的水位数据
         * @param startTime
         * @param endTime
         */
        void loadWaterLevelDataByDate(String startTime, String endTime);


        /**
         * 加载当前水位信息
         */
        void loadCurrentWaterLevelInfo();

        /**
         * 跳转至ChartActivity
         */
        void jumpToChartActivity();

        /**
         * 加载所有站点信息
         */
        void loadAllStationInfo();
    }

}
