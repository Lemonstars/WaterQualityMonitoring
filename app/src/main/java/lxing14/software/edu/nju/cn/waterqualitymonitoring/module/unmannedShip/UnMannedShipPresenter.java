package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.content.SharedPreferences;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.UnmannedBoatVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
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
    private int mStnId;

    public UnMannedShipPresenter(UnMannedShipContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void loadBoatCurrentLocation() {
        List<LatLng> latLngList = new ArrayList<>();
        // TODO 实时的数据获取
//        Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .flatMap(aLong -> RetrofitHelper.getWaterQualityInterface().getUnmannedBoatInfo(mStnId))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(vo -> {
//                    float latitude = (float) vo.getLatitude();
//                    float longitude = (float) vo.getLongitude();
//
//                    latLngList.add(new LatLng(latitude, longitude));
//                    mView.addBoatRealTimeLocation(latLngList);
//
//                    String today = TimeUtil.getCurrentTime();
//                    StringBuilder tPh_sb = new StringBuilder();
//                    tPh_sb.append("温度:");
//                    tPh_sb.append(vo.getTemperature());
//                    tPh_sb.append("°C  ");
//                    tPh_sb.append("ph:");
//                    tPh_sb.append(vo.getPh());
//                    mView.showWaterQualityNum(today, tPh_sb.toString());
//                });

        // TODO 获取历史数据
        RetrofitHelper.getWaterQualityInterface().getUnmannedBoatHisInfo(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<UnmannedBoatVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<UnmannedBoatVO> unmannedBoatVOS) {
                        List<LatLng> latLngs = new ArrayList<>();
                        for(UnmannedBoatVO vo: unmannedBoatVOS){
                            LatLng latLng = new LatLng(vo.getLatitude(), vo.getLongitude());
                            latLngs.add(latLng);
                        }
                        mView.addBoatHistoryLocation(latLngs);

                        String currentTime = TimeUtil.getCurrentTime();
                        UnmannedBoatVO vo = unmannedBoatVOS.get(0);
                        StringBuilder tPh_sb = new StringBuilder();
                        tPh_sb.append("温度:");
                        tPh_sb.append(vo.getTemperature());
                        tPh_sb.append("°C  ");
                        tPh_sb.append("ph:");
                        tPh_sb.append(vo.getPh());
                        mView.showWaterQualityNum(currentTime, tPh_sb.toString());
                    }
                });


    }

}
