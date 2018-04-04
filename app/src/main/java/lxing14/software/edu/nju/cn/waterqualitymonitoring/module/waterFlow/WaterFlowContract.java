package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

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

    }

    interface Presenter extends BasePresenter{

        /**
         * 获取默认的流量数据
         */
        void loadDefaultWaterFlowData();

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

    }

}


