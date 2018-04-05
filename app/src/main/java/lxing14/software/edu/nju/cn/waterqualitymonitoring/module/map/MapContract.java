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

        /**
         * 显示站点位置
         * @param markerOptionsList
         */
        void showStationLocation(ArrayList<MarkerOptions> markerOptionsList);

        /**
         * 显示定位
         *
         * @param latitude
         * @param longitude
         */
        void showLocation(double latitude, double longitude);

    }

    interface Presenter extends BasePresenter{

        /**
         * 加载所有站点信息
         */
        void loadAllWaterTypeInfo();

        /**
         * 加载水位信息
         */
        void loadWaterLevelInfo();

        /**
         * 加载水质信息
         */
        void loadWaterQualityInfo();

        /**
         * 加载流量信息
         */
        void loadWaterFlowInfo();

        /**
         * 加载漂浮物信息
         */
        void loadFloatingMaterialInfo();

        /**
         * 加载无人船信息
         */
        void loadUnmannedShipInfo();

        /**
         * 搜索
         * @param input
         */
        void search(String input);

    }

}
