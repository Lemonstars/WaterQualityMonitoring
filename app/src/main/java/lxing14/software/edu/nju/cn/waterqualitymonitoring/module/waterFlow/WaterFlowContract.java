package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/15
 * @time : 下午8:31
 */

public interface WaterFlowContract {

    interface View extends BaseView<WaterFlowContract.Presenter>{

        /**
         * 显示选择相机的UI
         * @param num
         */
        void showCameraChoiceView(int num);

        /**
         * 展示实时数据
         * @param dateList
         * @param dataList
         */
        void showRealTimeChart(List<String> dateList, List<Float> dataList);

        /**
         * 显示按天数据
         */
        void showDayChart();

        /**
         * 显示按月数据
         */
        void showMonthChart();

    }

    interface Presenter extends BasePresenter{

        /**
         * 获取图表数据
         */
        void loadChartData();

    }

}


