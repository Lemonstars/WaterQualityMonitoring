package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/28
 * @time : 下午9:13
 */

public interface ChartContract {

    interface View extends BaseView<ChartContract.Presenter>{

        /**
         * 展示图表
         * @param dateList
         * @param dataList
         */
        void showChart(List<String> dateList, List<Float> dataList);

        /**
         * 显示图表名称
         * @param name
         */
        void showChartName(String name);

        /**
         * 显示图表单位
         * @param unit
         */
        void showChartUnit(String unit);
    }

    interface Presenter extends BasePresenter{

        /**
         * 加载图表数据
         */
        void loadChartData();

    }

}
