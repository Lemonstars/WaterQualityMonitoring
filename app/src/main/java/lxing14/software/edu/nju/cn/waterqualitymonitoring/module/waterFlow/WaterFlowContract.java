package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

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

    }

    interface Presenter extends BasePresenter{

    }

}


