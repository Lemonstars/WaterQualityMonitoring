package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityTypeNumVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.MapMarkerConfig;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午7:22
 */

public class WaterQualityPresenter implements WaterQualityContract.Presenter {

    private WaterQualityContract.View mView;
    private int mState = WaterQualityData.TEMPERATURE;
    private int mStnId;

    private String startTime;
    private String endTime;
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Float> dataList = new ArrayList<>();

    public WaterQualityPresenter(WaterQualityContract.View mView, int stnId) {
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
        Intent intent = ChartActivity.generateIntent(context, WaterQualityData.NAME_CHINESE[mState], WaterQualityData.UNIT[mState], startTime, endTime, dateList, dataStrList);
        context.startActivity(intent);
    }

    @Override
    public void loadChartDataByDate(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        RetrofitHelper.getWaterQualityInterface().getWaterQualityInfo(mStnId, WaterQualityData.NAME_ENGLISH[mState], startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterQualityTypeNumVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterQualityTypeNumVO> waterQualityTypeNumVOS) {
                        dateList.clear();
                        dataList.clear();
                        int len = waterQualityTypeNumVOS.size();
                        WaterQualityTypeNumVO waterQualityTypeNumVO;
                        String numStr;
                        float num;
                        for(int i=0;i<len;i++){
                            waterQualityTypeNumVO = waterQualityTypeNumVOS.get(i);

                            numStr = waterQualityTypeNumVO.getReturnDateValue();
                            num = Float.parseFloat(numStr);
                            dataList.add(num);

                            dateList.add(waterQualityTypeNumVO.getCollectionTime());
                        }

                        mView.showChartUnit(WaterQualityData.UNIT[mState]);
                        mView.configChartMarkerView(WaterQualityData.NAME_CHINESE[mState]+":", WaterQualityData.UNIT[mState]);
                        mView.showWaterQualityChart(dateList, dataList);
                    }
                });
    }

    @Override
    public void loadChartDataByType(int type) {
        if(type == mState){
            return;
        }
        this.mState = type;
        loadChartDataByDate(startTime, endTime);
    }

    @Override
    public void loadCurrentWaterQualityInfo() {
        RetrofitHelper.getWaterQualityInterface().getCurrentWaterQualityInfo(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WaterQualityVO>(mView.getContextView()) {
                    @Override
                    public void onNext(WaterQualityVO vo) {

                        List<Double> waterQualityList = new ArrayList<>(18);
                        waterQualityList.add(Double.parseDouble(vo.getTemperature()));
                        waterQualityList.add(vo.getConductivity());
                        waterQualityList.add(vo.getPh());
                        waterQualityList.add(vo.getDissolvedOxygen());
                        waterQualityList.add(vo.getRedox());
                        waterQualityList.add(vo.getTurbidity());
                        waterQualityList.add(vo.getTransparency());
                        waterQualityList.add(vo.getNH3());
                        waterQualityList.add(vo.getSalinity());
                        waterQualityList.add(vo.getTds());
                        waterQualityList.add(vo.getDensity());
                        waterQualityList.add(vo.getDoSaturation());
                        waterQualityList.add(vo.getTss());
                        waterQualityList.add(vo.getChlorophylA());
                        waterQualityList.add(vo.getPhycocyanobilin());
                        waterQualityList.add(vo.getHatchOpen());
                        waterQualityList.add(vo.getWaterPenetration());
                        waterQualityList.add(vo.getVoltage());

                        mView.showCurrentWaterQualityInfo(waterQualityList);
                    }
                });
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
