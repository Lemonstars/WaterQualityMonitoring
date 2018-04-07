package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午3:47
 */

public interface UnMannedShipContract {

    interface View extends BaseView<Presenter>{

        /**
         * 显示水质图表
         */
        void showWaterQualityChart(List<String> dateList, List<Float> dataList);

        /**
         * 显示被选中的tab
         * @param index
         */
        void showTabSelected(int index);

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
         * 根据类型获取当前图表数据
         * @param type
         */
        void loadChartDataByType(int type);

    }

}
