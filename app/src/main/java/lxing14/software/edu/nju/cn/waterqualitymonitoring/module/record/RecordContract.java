package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.PdfVO;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/13
 * @time : 上午10:40
 */

public interface RecordContract {

    interface View extends BaseView<RecordContract.Presenter>{

        /**
         * 显示站点位置
         * @param markerOptionsList
         */
        void showStationLocation(ArrayList<MarkerOptions> markerOptionsList);

        /**
         * 显示pdf数据
         * @param list
         */
        void showPDFData(List<PdfVO> list);

        /**
         * 显示图片
         * @param picUrlList
         */
        void showPic(List<String> picUrlList);
    }

    interface Presenter extends BasePresenter{

        /**
         * 加载所有站点信息
         */
        void loadAllStationInfo();

        /**
         * 加载记录信息
         */
        void loadRecordData();

        /**
         * 下载PDF文件
         * @param url
         */
        void downloadPdfFile(String url);
    }

}
