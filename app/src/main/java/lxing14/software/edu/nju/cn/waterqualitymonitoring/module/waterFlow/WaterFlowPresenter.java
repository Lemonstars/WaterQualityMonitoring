package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/15
 * @time : 下午8:32
 */

public class WaterFlowPresenter implements WaterFlowContract.Presenter {

    public static final int REAL_TIME = 0;
    public static final int DAY = 1;
    public static final int MONTH = 2;

    private WaterFlowContract.View mView;
    private int mState = REAL_TIME;


    public WaterFlowPresenter(WaterFlowContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        //TODO 根据state获取何种类型数据
        loadChartData();
        mView.showCameraChoiceView(5);
    }

    @Override
    public void loadChartData() {
        RetrofitHelper.getWaterFlowInterface().getLatestWaterFlow(1, CommonConstant.DEFAULT_DAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WaterFlowVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterFlowVO> waterFlowVOList) {
                        int len = waterFlowVOList.size();
                        List<String> dateList = new ArrayList<>(len);
                        List<Float> dataList = new ArrayList<>(len);

                        WaterFlowVO waterFlowVO;
                        for(int i=0;i<len;i++){
                            waterFlowVO = waterFlowVOList.get(len-i-1);
                            dateList.add(waterFlowVO.getCollectionTime());
                            dataList.add((float)(waterFlowVO.getAvgFlow()));
                        }

                        mView.showRealTimeChart(dateList, dataList);
                    }
                });
    }



}
