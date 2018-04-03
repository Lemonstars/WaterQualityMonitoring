package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

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
         * 显示被选中的tab
         * @param index
         */
        void showTabSelected(int index);

        /**
         * 显示图表的单位
         * @param index
         */
        void showChartUnit(int index);

        /**
         * 显示水质图表
         * @param dataList
         * @param dateList
         */
        void showWaterQualityChart(List<String> dateList, List<Float> dataList);

        /**
         * 显示当前水质数据
         * @param temperature 温度
         * @param conductivity 电导率
         * @param ph ph值
         * @param dissolvedOxygen 溶解氧
         * @param redox 氧化还原
         * @param turbidity 浊度
         * @param transparency 透明度
         * @param nh3 氨氮
         */
        void showCurrentWaterQualityInfo(String temperature, double ph, double dissolvedOxygen, double redox,
                                         double transparency, double conductivity, double turbidity, double nh3);
    }

    interface Presenter extends BasePresenter{

        /**
         * 根据类型获取当前图表数据
         * @param type
         */
        void loadChartDataByType(int type);

        /**
         * 根据类型和起止时间获取房前图表数据
         * @param startTime
         * @param endTime
         */
        void loadChartDataByDateAndType(String startTime, String endTime);

        /**
         * 获取当前水质数据
         */
        void loadCurrentWaterQualityInfo();

    }

}
