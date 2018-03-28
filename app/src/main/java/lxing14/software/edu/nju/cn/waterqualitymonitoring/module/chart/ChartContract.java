package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

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
         */
        void showChart();

    }

    interface Presenter extends BasePresenter{

    }

}
