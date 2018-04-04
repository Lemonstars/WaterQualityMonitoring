package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterTypeEnum;
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
    private AMapLocationClientOption mLocationOption;

    public MapPresenter(@NonNull MapContract.View view){
        mMapView = view;
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {
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
        mLocationClient = new AMapLocationClient(mMapView.getContextView());
        mLocationClient.setLocationListener(aMapLocation -> {
            double latitude = CommonConstant.LATITUDE_OF_NJ;
            double longitude = CommonConstant.LONGITUDE_OF_NJ;
            if(aMapLocation.getErrorCode() == 0){
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
            }
            mMapView.showCurrentLocation(latitude, longitude);
        });

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setMockEnable(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void loadPointInfo(int waterType){
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mMapView.getContextView()) {
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

                        mMapView.showInitPoint(markerOptionsArrayList);
                    }
                });
    }
}
