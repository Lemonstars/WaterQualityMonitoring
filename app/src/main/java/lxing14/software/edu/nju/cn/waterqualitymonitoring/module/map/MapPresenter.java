package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterMapInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterTypeEnum;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/20
 * @time : 下午2:55
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    private AMapLocationClient mLocationClient;
    private AMapLocationListener mLocationListener;
    private AMapLocationClientOption mLocationOption;

    public MapPresenter(@NonNull MapContract.View view){
        mMapView = view;
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {
        loadAllWaterTypeInfo();
        initLocation();
    }

    @Override
    public void loadAllWaterTypeInfo() {
        loadPointInfo(WaterTypeEnum.ALL);
    }

    @Override
    public void loadWaterLevelInfo() {
        loadPointInfo(WaterTypeEnum.WATER_LEVEL);
    }

    @Override
    public void loadWaterForceInfo() {
        loadPointInfo(WaterTypeEnum.WATER_FORCE);
    }

    @Override
    public void loadWaterQualityInfo() {
        loadPointInfo(WaterTypeEnum.WATER_QUALITY);
    }

    @Override
    public void loadFloatingMaterialInfo() {
        loadPointInfo(WaterTypeEnum.FLOATING_MATERIAL);
    }

    @Override
    public void loadUnmannedShipInfo() {

    }

    private void initLocation() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double latitude = CommonConstant.LATITUDE_OF_BJ;
                double longitude = CommonConstant.LONGITUDE_OF_BJ;
                if(aMapLocation.getErrorCode() == 0){
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                }
                mMapView.showCurrentLocation(latitude, longitude);
            }
        };

        mLocationClient = new AMapLocationClient(mMapView.getViewContext());
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setMockEnable(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void loadPointInfo(int waterType){
        RetrofitHelper.getWaterInterface().getMapInfo(waterType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WaterMapInfoVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterMapInfoVO> waterMapInfoVOs) {
                        LatLng latLng;
                        ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>(waterMapInfoVOs.size());
                        for(WaterMapInfoVO vo: waterMapInfoVOs){
                            double x = Double.parseDouble(vo.getX());
                            double y = Double.parseDouble(vo.getY());

                            latLng = new LatLng(y, x);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng).title(vo.getStnName()).snippet(String.valueOf(vo.getId()));
                            markerOptionsArrayList.add(markerOptions);
                        }

                        mMapView.showInitPoint(markerOptionsArrayList);
                    }
                });
    }
}
