package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午2:05
 */

public interface WaterFloatingContract {

    interface IView extends BaseView<IPresenter> {

        /**
         * 显示漂浮物柱状图
         */
        void showBarChart();

    }

    interface IPresenter extends BasePresenter{

    }

}
