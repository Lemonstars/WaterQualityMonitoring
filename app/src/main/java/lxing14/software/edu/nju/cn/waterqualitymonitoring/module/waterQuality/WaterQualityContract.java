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

    }

    interface Presenter extends BasePresenter{

    }

}
