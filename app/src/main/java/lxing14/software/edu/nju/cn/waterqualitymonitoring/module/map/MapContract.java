package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;

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

        /**
         * 获取上下文
         */
        Context getViewContext();

        /**
         * 显示当前定位
         *
         * @param latitude
         * @param longitude
         */
        void showCurrentLocation(double latitude, double longitude);

    }

    interface Presenter extends BasePresenter{

        void loadAllWaterTypeInfo();

        /**
         * 加载水位信息
         */
        void loadWaterLevelInfo();

        /**
         * 加载水动力信息
         */
        void loadWaterForceInfo();

        /**
         * 加载水质信息
         */
        void loadWaterQualityInfo();

        /**
         * 加载漂浮物信息
         */
        void loadFloatingMaterialInfo();

        /**
         * 加载无人船信息
         */
        void loadUnmannedShipInfo();


    }

}
