package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午3:47
 */

public interface UnMannedShipContract {

    interface IView extends BaseView<UnMannedShipContract.IPresenter>{

        /**
         * 显示水质图表
         */
        void showWaterQualityChart();

    }

    interface IPresenter extends BasePresenter{

    }

}
