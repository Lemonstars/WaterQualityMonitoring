package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/20
 * @time : 下午2:55
 */

public interface MapContract {

    interface View extends BaseView<Presenter>{

        void showInitPoint(ArrayList<MarkerOptions> markerOptionsList);

    }

    interface Presenter extends BasePresenter{

        void loadAllWaterTypeInfo();

        void loadWaterLevelInfo();

        void loadWaterForceInfo();

        void loadWaterQualityInfo();

        void loadFloatingMaterialInfo();

    }

}
