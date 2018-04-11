package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityTypeNumVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午3:48
 */

public class UnMannedShipPresenter implements UnMannedShipContract.Presenter {

    private UnMannedShipContract.View mView;

    private int mState = 0;
    private int mStnId;

    //TODO 等待确认
    private String[] mChartUnit = new String[]{"°C", "S/m", "", "mg/L", "mV", "NTU", "%", "mg/L"};
    private String[] mEntry = new String[]{"温度", "电导率", "ph值", "溶解氧", "氧化还原", "浊度", "透明度", "氨氮"};

    private String startTime= TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Float> dataList = new ArrayList<>();

    public UnMannedShipPresenter(UnMannedShipContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void jumpToChartActivity() {
        ArrayList<String> dataStrList = new ArrayList<>(dataList.size());
        for(Float num: dataList){
            dataStrList.add(String.valueOf(num));
        }

        Context context = mView.getContextView();
        Intent intent = ChartActivity.generateIntent(context, mEntry[mState], mChartUnit[mState], startTime, endTime, dateList, dataStrList);
        context.startActivity(intent);
    }

    @Override
    public void loadChartDataByType(int type) {
        this.mState = type;
        loadChartDataByDate(startTime, endTime);
    }

    @Override
    public void loadChartDataByDate(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
//        RetrofitHelper.getWaterQualityInterface().getWaterQualityInfo(mStnId, WaterQualityData.getEnglishName(mState), startTime, endTime)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<List<WaterQualityTypeNumVO>>(mView.getContextView()) {
//                    @Override
//                    public void onNext(List<WaterQualityTypeNumVO> waterQualityTypeNumVOS) {
//                        dateList.clear();
//                        dataList.clear();
//                        int len = waterQualityTypeNumVOS.size();
//                        WaterQualityTypeNumVO waterQualityTypeNumVO;
//                        String numStr;
//                        float num;
//                        for(int i=0;i<len;i++){
//                            waterQualityTypeNumVO = waterQualityTypeNumVOS.get(i);
//
//                            numStr = waterQualityTypeNumVO.getReturnDateValue();
//                            num = Float.parseFloat(numStr);
//                            dataList.add(num);
//
//                            dateList.add(waterQualityTypeNumVO.getCollectionTime());
//                        }
//
//                        mView.showTabSelected(mState);
//                        mView.showChartUnit(mChartUnit[mState]);
//                        mView.configChartMarkerView(mEntry[mState]+":", mChartUnit[mState]);
//                        mView.showWaterQualityChart(dateList, dataList);
//                    }
//                });
    }

    @Override
    public void loadBoatCurrentLocation() {

        //TODO 应该用后端接口
//        Random random = new Random();
//        List<LatLng> latLngList = new ArrayList<>();
//        Observable.interval(500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( aLong -> {
//                    float latitude = 39.904989f + random.nextFloat()/10000;
//                    float longitude = 116.405285f + random.nextFloat()/10000;
//
//                    mView.showCenterPoint(latitude, longitude);
//                    latLngList.add(new LatLng(latitude, longitude));
//                    mView.addBoatLocation(latLngList);
//                });

    }
}
