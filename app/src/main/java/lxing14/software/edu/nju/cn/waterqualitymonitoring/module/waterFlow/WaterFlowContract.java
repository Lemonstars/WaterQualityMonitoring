package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

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
         * 显示n个相机
         * @param num
         */
        void showCameraLayout(int num);

        /**
         * 显示站点位置
         * @param markerOptionsList
         */
        void showStationLocation(ArrayList<MarkerOptions> markerOptionsList);

        /**
         * 展示流量图表数据
         * @param dateList
         * @param dataList
         */
        void showWaterFlowChartData(List<String> dateList, List<Float> dataList);

        /**
         * 展示相机数据
         * @param index
         * @param date
         * @param picUrl
         * @param waterFlow
         * @param waterSpeed
         */
        void showCameraInfo(int index, String date, String picUrl, double waterFlow, double waterSpeed);

        /**
         * 隐藏摄像机
         */
        void hideCameraLayout();
    }

    interface Presenter extends BasePresenter{

        /**
         * 跳转至ChartActivity
         */
        void jumpToChartActivity();

        /**
         * 获取指定时间范围内的流量数据
         * @param startTime
         * @param endTime
         */
        void loadWaterFlowDataByDate(String startTime, String endTime);


        /**
         * 获取流量视频url
         */
        void loadWaterFlowVideoUrl();

        /**
         * 从网络获取相机信息
         */
        void loadCameraInfoFromNetwork();

        /**
         * 获取相机信息
         * @param index
         */
        void loadCameraInfo(int index);

        /**
         * 处理tab点击
         * @param index
         */
        void processTab(int index);


        /**
         * 加载所有站点信息
         */
        void loadAllStationInfo();

    }

}


