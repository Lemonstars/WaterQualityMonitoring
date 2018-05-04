package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingByDateVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingPicVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.MapMarkerConfig;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午2:06
 */

public class WaterFloatingPresenter implements WaterFloatingContract.Presenter {

    private WaterFloatingContract.View mView;
    private int mStnId;

    private String startTime = TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Integer> dataList = new ArrayList<>();

    public WaterFloatingPresenter(WaterFloatingContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void loadWaterFloatingChartByDate(String startTime, String endTime) {
        RetrofitHelper.getWaterFloatingInterface().getFloatingInfoByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFloatingByDateVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFloatingByDateVO> waterFloatingByDateVOS) {
                        dateList.clear();
                        dataList.clear();
                        int len = waterFloatingByDateVOS.size();
                        WaterFloatingByDateVO vo;
                        for(int i=0;i<len;i++){
                            vo = waterFloatingByDateVOS.get(len-1-i);
                            dateList.add(vo.getDays());
                            dataList.add((vo.getNums()) );
                        }
                        mView.showFloatingChart(dateList, dataList);
                    }
                });
    }

    @Override
    public void loadWaterFloatingPicURl() {
        RetrofitHelper.getWaterFloatingInterface().getFloatingInfoPic(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFloatingPicVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFloatingPicVO> waterFloatingPicVOS) {
                        String url0 = waterFloatingPicVOS.get(0).getFilePathResult();
                        String url1 = waterFloatingPicVOS.get(1).getFilePathResult();
                        String url2 = waterFloatingPicVOS.get(2).getFilePathResult();
                        String url3 = waterFloatingPicVOS.get(3).getFilePathResult();
                        mView.showFloatingPic(url0, url1, url2, url3);
                    }
                });
    }

    @Override
    public void processTab(int index) {
        endTime = TimeUtil.getTodayDate();
        switch (index){
            case CommonConstant.ONE_WEEK:
                startTime = TimeUtil.getDateBeforeNum(7);
                break;
            case CommonConstant.ONE_MONTH:
                startTime = TimeUtil.getDateBeforeNum(30);
                break;
            case CommonConstant.THREE_MONTH:
                startTime = TimeUtil.getDateBeforeNum(90);
                break;
        }
        loadWaterFloatingChartByDate(startTime, endTime);
    }

    @Override
    public void jumpToChartActivity() {
        ArrayList<String> dataStrList = new ArrayList<>(dataList.size());
        for(Integer num: dataList){
            dataStrList.add(String.valueOf(num));
        }

        Context context = mView.getContextView();
        Intent intent = ChartActivity.generateIntent(context, "漂浮物", "个",startTime, endTime, dateList, dataStrList);
        context.startActivity(intent);
    }

    @Override
    public void loadAllStationInfo() {
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterStationInfoVO> waterStationInfoVOS) {
                        new MapMarkerConfig(){
                            @Override
                            public void showStationLocation(ArrayList<MarkerOptions> list) {
                                mView.showStationLocation(list);
                            }
                        }.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
                    }
                });
    }
}
