package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityTypeNumVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.OrderConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterTypeEnum;
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
    public void start() {

    }

    @Override
    public void jumpToChartActivity() {
        ArrayList<String> dataStrList = new ArrayList<>(dataList.size());
        for(Float num: dataList){
            dataStrList.add(String.valueOf(num));
        }

        Context context = mView.getContextView();
        Intent intent = ChartActivity.generateIntent(context, WaterTypeEnum.WATER_QUALITY, false, startTime, endTime, dateList, dataStrList);
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
        RetrofitHelper.getWaterQualityInterface().getWaterQualityInfo(mStnId, WaterQualityData.getName(mState), startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterQualityTypeNumVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterQualityTypeNumVO> waterQualityTypeNumVOS) {
                        onNetworkRequest(waterQualityTypeNumVOS, OrderConstant.ASC);
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
                    public void onNext(WaterQualityVO waterQualityVO) {
                        String temperature = waterQualityVO.getTemperature();
                        double ph = waterQualityVO.getPh()==null?-1:waterQualityVO.getPh();
                        double dissolvedOxygen = waterQualityVO.getDissolvedOxygen()==null?-1:waterQualityVO.getDissolvedOxygen();
                        double redox = waterQualityVO.getRedox()==null?-1:waterQualityVO.getRedox();
                        double transparency = waterQualityVO.getTransparency()==null?-1:waterQualityVO.getTransparency();
                        double conductivity = waterQualityVO.getConductivity()==null?-1:waterQualityVO.getConductivity();
                        double turbidity = waterQualityVO.getTurbidity()==null?-1:waterQualityVO.getTurbidity();
                        double nh3 = waterQualityVO.getNH3()==null?-1:waterQualityVO.getNH3();

                        mView.showCurrentWaterQualityInfo(temperature, ph, dissolvedOxygen, redox,
                                transparency, conductivity, turbidity, nh3);
                    }
                });
    }

    private void onNetworkRequest(List<WaterQualityTypeNumVO> waterQualityTypeVOS, OrderConstant orderConstant){
        dateList.clear();
        dataList.clear();
        int len = waterQualityTypeVOS.size();
        WaterQualityTypeNumVO waterQualityTypeNumVO;
        String numStr;
        float num;
        for(int i=0;i<len;i++){
            if(orderConstant == OrderConstant.ASC){
                waterQualityTypeNumVO = waterQualityTypeVOS.get(i);
            }else {
                waterQualityTypeNumVO = waterQualityTypeVOS.get(len-i-1);
            }

            numStr = waterQualityTypeNumVO.getReturnDateValue();
            num = Float.parseFloat(numStr);
            dataList.add(num);

            dateList.add(waterQualityTypeNumVO.getCollectionTime());
        }

        mView.showTabSelected(mState);
        mView.showChartUnit(mState);
        mView.showWaterQualityChart(dateList, dataList);
    }

}
