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
         * 显示选择相机的UI
         * @param num
         */
        void showCameraChoiceView(int num);

        /**
         * 展示流量图表数据
         * @param dateList
         * @param dataList
         */
        void showWaterFlowChartData(List<String> dateList, List<Float> dataList);

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
        void loadWaterLevelDataByDate(String startTime, String endTime);

        /**
         * 处理tab点击
         * @param index
         */
        void processTab(int index);

    }

}


