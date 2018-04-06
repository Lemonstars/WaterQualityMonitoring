package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午2:05
 */

public interface WaterFloatingContract {

    interface IView extends BaseView<IPresenter> {

        /**
         * 显示漂浮物柱状图
         * @param dateList
         * @param dataList
         */
        void showBarChart(List<String> dateList, List<Integer> dataList);

        /**
         * 显示漂浮物图片
         * @param url1
         * @param url2
         * @param url3
         */
        void showFloatingPic(String url1, String url2, String url3);
    }

    interface IPresenter extends BasePresenter{

        /**
         * 根据日期范围获取漂浮物数据
         * @param startTime
         * @param endTime
         */
        void loadWaterFloatingChartByDate(String startTime, String endTime);

        /**
         * 获取漂浮物图片url
         */
        void loadWaterFloatingPicURl();
    }

}
