package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.OrderConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
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

    private WaterFlowContract.View mView;
    private int mStnId;

    public WaterFlowPresenter(WaterFlowContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDefaultWaterFlowData();
        mView.showCameraChoiceView(5);
    }

    @Override
    public void loadDefaultWaterFlowData() {
        RetrofitHelper.getWaterFlowInterface().getLatestWaterFlow(mStnId, 30)
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
                         onNetworkRequest(OrderConstant.DESC, waterFlowVOList);
                    }
                });
    }

    @Override
    public void loadWaterLevelDataByDate(String startTime, String endTime) {
        RetrofitHelper.getWaterFlowInterface().getWaterFlowByDate(mStnId, startTime, endTime)
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
                        onNetworkRequest(OrderConstant.ASC, waterFlowVOList);
                    }
                });
    }

    @Override
    public void processTab(int index) {
        if(index == CommonConstant.REAL_TIME){
            loadDefaultWaterFlowData();
        }else {
            String startTime;
            String endTime = TimeUtil.getTodayDate();
            if(index == CommonConstant.DAY){
                startTime = TimeUtil.getDateBeforeNum(15);
            }else {
                startTime = TimeUtil.getDateBeforeNum(30);
            }
            loadWaterLevelDataByDate(startTime, endTime);
        }
    }

    private void onNetworkRequest(OrderConstant orderConstant, List<WaterFlowVO> waterFlowVOList){
        int len = waterFlowVOList.size();
        List<String> dateList = new ArrayList<>(len);
        List<Float> dataList = new ArrayList<>(len);

        WaterFlowVO waterFlowVO;
        for(int i=0;i<len;i++){
            if(orderConstant == OrderConstant.ASC){
                waterFlowVO = waterFlowVOList.get(i);
            }else {
                waterFlowVO = waterFlowVOList.get(len-i-1);
            }
            dateList.add(waterFlowVO.getCollectionTime());
            dataList.add((float)(waterFlowVO.getAvgFlow()));
        }

        mView.showWaterFlowChartData(dateList, dataList);
    }


}
