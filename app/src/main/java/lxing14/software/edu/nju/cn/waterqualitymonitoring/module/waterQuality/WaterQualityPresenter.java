package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.util.Log;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityVO;
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

    private WaterQualityContract.View mView;

    public WaterQualityPresenter(WaterQualityContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showWaterQualityChart();
        loadCurrentWaterQualityInfo();
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
