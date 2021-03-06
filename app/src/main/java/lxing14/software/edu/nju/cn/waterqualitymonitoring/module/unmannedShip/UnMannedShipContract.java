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
         * 显示水质数据
         * @param date
         * @param tPh
         */
        void showWaterQualityNum(String date, String tPh);

        /**
         * 添加无人船位置实时标记
         * @param points
         */
        void addBoatRealTimeLocation(List<LatLng> points);

        /**
         * 添加无人船历史标记
         * @param points
         */
        void addBoatHistoryLocation(List<LatLng> points);
    }

    interface Presenter extends BasePresenter{

        /**
         * 加载无人船当前位置
         */
        void loadBoatCurrentLocation();

    }

}
