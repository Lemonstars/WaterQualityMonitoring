package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

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
         * 显示水质图表
         */
        void showWaterQualityChart();

        /**
         * 显示当前水质数据
         * @param temperature 温度
         * @param ph ph值
         * @param dissolvedOxygen 溶解氧
         * @param redox 氧化还原
         * @param transparency 透明度
         * @param conductivity 电导率
         * @param turbidity 浊度
         * @param nh3 氨氮
         */
        void showCurrentWaterQualityInfo(String temperature, double ph, double dissolvedOxygen, double redox,
                                         double transparency, double conductivity, double turbidity, double nh3);
    }

    interface Presenter extends BasePresenter{

        /**
         * 获取当前水质数据
         */
        void loadCurrentWaterQualityInfo();

    }

}
