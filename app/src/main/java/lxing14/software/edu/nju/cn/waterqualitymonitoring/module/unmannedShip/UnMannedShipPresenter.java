package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
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
    public void loadBoatCurrentLocation() {

        List<LatLng> latLngList = new ArrayList<>();

//        Observable.interval(100, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .flatMap( aLong -> RetrofitHelper.getWaterQualityInterface().getUnmannedBoatInfo(mStnId))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(vo -> {
//                    float latitude = (float) vo.getLatitude();
//                    float longitude = (float) vo.getLongitude();
//
//                    mView.showCenterPoint(latitude, longitude);
//                    latLngList.add(new LatLng(latitude, longitude));
//                    mView.addBoatLocation(latLngList);
//
//                    String today = TimeUtil.getCurrentTime();
//                    StringBuilder tPh_sb = new StringBuilder();
//                    tPh_sb.append("温度:");
//                    tPh_sb.append(vo.getTemperature());
//                    tPh_sb.append("°C  ");
//                    tPh_sb.append("ph:");
//                    tPh_sb.append(vo.getPh());
//                    StringBuilder o2_sb = new StringBuilder();
//                    o2_sb.append("溶解氧:");
//                    o2_sb.append(vo.getDissolvedOxygen());
//                    o2_sb.append("  ");
//                    o2_sb.append("氧化还原:");
//                    o2_sb.append(vo.getRedox());
//
//                    mView.showWaterQualityNum(today, tPh_sb.toString(), o2_sb.toString());
//                });
    }
}
