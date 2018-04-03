package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

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
         * 显示水位默认图标数据
         * @param waterLevelDate
         * @param waterLevelData
         */
        void showWaterLevelInfo(List<String> waterLevelDate, List<Float> waterLevelData);

        /**
         * 显示当前水位信息
         * @param picUrl
         * @param currentWaterLevel
         * @param historicalWaterLevel
         * @param photoByDate
         */
        void showCurrentWaterLevelDetailInfo(String picUrl, String currentWaterLevel,
                                             String historicalWaterLevel, String photoByDate);

        /**
         * 地图定位当前位置
         */
        void showCurrentLocation(double latitude, double longitude);
    }

    interface Presenter extends BasePresenter{

        /**
         * 处理tab点击
         * @param index
         */
        void processTab(int index);

        /**
         * 获取默认的水位数据
         */
        void loadDefaultWaterLevelData();

        /**
         * 获取指定范围内的水位数据
         * @param startTime
         * @param endTime
         */
        void loadWaterLevelDataByDate(String startTime, String endTime);


        void getCurrentWaterLevelInfo();

        /**
         * 获取当前坐标信息
         */
        void loadLocationInfo();
    }

}
