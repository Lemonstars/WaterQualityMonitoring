package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.content.SharedPreferences;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.Observable;
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
    private int mStnId;


    public UnMannedShipPresenter(UnMannedShipContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void loadInitLocation() {
        SharedPreferences sharedPreferences = mView.getContextView().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);
        mView.showCenterPoint(latitude, longitude);
    }

    @Override
    public void loadBoatCurrentLocation() {
        List<LatLng> latLngList = new ArrayList<>();
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(aLong -> RetrofitHelper.getWaterQualityInterface().getUnmannedBoatInfo(mStnId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vo -> {
                    float latitude = (float) vo.getLatitude();
                    float longitude = (float) vo.getLongitude();

                    latLngList.add(new LatLng(latitude, longitude));
                    mView.addBoatLocation(latLngList);

                    String today = TimeUtil.getCurrentTime();
                    StringBuilder tPh_sb = new StringBuilder();
                    tPh_sb.append("温度:");
                    tPh_sb.append(vo.getTemperature());
                    tPh_sb.append("°C  ");
                    tPh_sb.append("ph:");
                    tPh_sb.append(vo.getPh());
                    mView.showWaterQualityNum(today, tPh_sb.toString());
                });
    }
}
