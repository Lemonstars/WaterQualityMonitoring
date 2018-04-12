package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
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

        Observable.interval(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap( aLong -> RetrofitHelper.getWaterQualityInterface().getUnmannedBoatInfo(mStnId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vo -> {
                    float latitude = (float) vo.getLatitude();
                    float longitude = (float) vo.getLongitude();

                    mView.showCenterPoint(latitude, longitude);
                    latLngList.add(new LatLng(latitude, longitude));
                    mView.addBoatLocation(latLngList);
                });


    }
}
