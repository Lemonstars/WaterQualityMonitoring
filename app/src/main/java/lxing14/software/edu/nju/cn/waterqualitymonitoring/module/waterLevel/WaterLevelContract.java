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
    }

    interface Presenter extends BasePresenter{

        void getDefaultWaterLevelInfo();

        void getCurrentWaterLevelInfo();
    }

}
