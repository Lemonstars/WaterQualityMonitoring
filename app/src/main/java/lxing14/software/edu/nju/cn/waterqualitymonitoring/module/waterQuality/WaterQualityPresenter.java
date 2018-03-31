package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.util.Log;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午7:22
 */

public class WaterQualityPresenter implements WaterQualityContract.Presenter {

    public static final int DEFAULT_DAY = 15;

    private WaterQualityContract.View mView;
    private int mState = WaterQualityData.TEMPERATURE;

    public WaterQualityPresenter(WaterQualityContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadChartDataByType(mState);
        loadCurrentWaterQualityInfo();
    }


    @Override
    public void loadChartDataByType(int type) {
        mState = type;

        RetrofitHelper.getWaterQualityInterface().getWaterQualityInfo(1, WaterQualityData.getName(mState), DEFAULT_DAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WaterQualityVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterQualityVO> waterQualityVOS) {
                        //TODO 看看改不改接口
                        mView.showTabSelected(mState);
                        mView.showWaterQualityChart();
                    }
                });
    }

    @Override
    public void loadCurrentWaterQualityInfo() {
        RetrofitHelper.getWaterQualityInterface().getCurrentWaterQualityInfo(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WaterQualityVO>() {
                    @Override
                    public void onCompleted() {
                        Log.d("WaterQualityPresenter", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("WaterQualityPresenter", "onError: ");
                    }

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
}
