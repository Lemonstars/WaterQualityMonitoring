package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.LatLng;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
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

    //TODO 等待确认
    private String[] mChartUnit = new String[]{"°C", "S/m", "", "mg/L", "mV", "NTU", "%", "mg/L"};
    private String[] mEntry = new String[]{"温度", "电导率", "ph值", "溶解氧", "氧化还原", "浊度", "透明度", "氨氮"};

    private String startTime= TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();
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
        Intent intent = ChartActivity.generateIntent(context, mEntry[mState], mChartUnit[mState], startTime, endTime, dateList, dataStrList);
        context.startActivity(intent);
    }

    @Override
    public void loadChartDataByDate(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        RetrofitHelper.getWaterQualityInterface().getWaterQualityInfo(mStnId, WaterQualityData.getEnglishName(mState), startTime, endTime)
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

                        mView.showChartUnit(mChartUnit[mState]);
                        mView.configChartMarkerView(mEntry[mState]+":", mChartUnit[mState]);
                        mView.showWaterQualityChart(dateList, dataList);
                    }
                });
    }

    @Override
    public void loadCurrentWaterQualityInfo() {
        RetrofitHelper.getWaterQualityInterface().getCurrentWaterQualityInfo(1)
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
                        LatLng latLng;
                        ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>(waterStationInfoVOS.size());
                        for(WaterStationInfoVO vo: waterStationInfoVOS){
                            double x = Double.parseDouble(vo.getX());
                            double y = Double.parseDouble(vo.getY());

                            latLng = new LatLng(y, x);
                            MarkerOptions markerOptions = new MarkerOptions();
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.valueOf(vo.getId()));
                            sb.append(' ');
                            sb.append(vo.getName());
                            sb.append(' ');
                            sb.append(vo.isHasWaterLevel()? 1:0); // water level
                            sb.append(' ');
                            sb.append(vo.isHasWaterQuality()? 1:0); // water quality
                            sb.append(' ');
                            sb.append(vo.isHasWaterFlow()? 1:0); // water flow
                            sb.append(' ');
                            sb.append(vo.isHasFloatingMaterial()? 1:0); // floating
                            sb.append(' ');
                            sb.append(vo.isHasUnmannedShip()? 1:0); // boat
                            sb.append(' ');
                            sb.append(y);
                            sb.append(' ');
                            sb.append(x);

                            markerOptions.position(latLng).snippet(sb.toString());
                            markerOptionsArrayList.add(markerOptions);
                        }

                        mView.showStationLocation(markerOptionsArrayList);
                    }
                });
    }
}
