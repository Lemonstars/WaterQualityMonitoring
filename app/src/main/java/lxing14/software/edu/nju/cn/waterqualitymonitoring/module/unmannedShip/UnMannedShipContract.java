package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import com.amap.api.maps.model.LatLng;

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
         * 显示中心点
         * @param latitude
         * @param longitude
         */
        void showCenterPoint(float latitude, float longitude);

        /**
         * 添加无人船位置标记
         * @param points
         */
        void addBoatLocation(List<LatLng> points);

    }

    interface Presenter extends BasePresenter{

        /**
         * 加载无人船当前位置
         */
        void loadBoatCurrentLocation();

    }

}
