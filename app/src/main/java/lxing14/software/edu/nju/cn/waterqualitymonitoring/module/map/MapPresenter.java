package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;
import android.widget.Toast;

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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.StringUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/20
 * @time : 下午2:55
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private List<WaterStationInfoVO> mWaterStationInfoList;

    public MapPresenter(@NonNull MapContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        initLocation();
    }

    @Override
    public void loadAllWaterTypeInfo() {
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterStationInfoVO> waterStationInfoVOS) {
                        mWaterStationInfoList = waterStationInfoVOS;
                        onRequestStationInfo(waterStationInfoVOS);
                    }
                });
    }

    @Override
    public void loadWaterLevelInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWaterLevel()){
                waterStationInfoVOS.add(vo);
            }
        }
        onRequestStationInfo(waterStationInfoVOS);
    }

    @Override
    public void loadWaterFlowInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWaterFlow()){
                waterStationInfoVOS.add(vo);
            }
        }
        onRequestStationInfo(waterStationInfoVOS);
    }

    @Override
    public void loadWaterQualityInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWaterQuality()){
                waterStationInfoVOS.add(vo);
            }
        }
        onRequestStationInfo(waterStationInfoVOS);
    }

    @Override
    public void loadFloatingMaterialInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasFloatingMaterial()){
                waterStationInfoVOS.add(vo);
            }
        }
        onRequestStationInfo(waterStationInfoVOS);
    }

    @Override
    public void loadUnmannedShipInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasUnmannedShip()){
                waterStationInfoVOS.add(vo);
            }
        }
        onRequestStationInfo(waterStationInfoVOS);
    }

    @Override
    public void search(String input) {
        if(StringUtil.isEmpty(input)){
            return;
        }

        ArrayList<WaterStationInfoVO> target = new ArrayList<>();
        double latitude = CommonConstant.LATITUDE_OF_NJ;
        double longitude = CommonConstant.LONGITUDE_OF_NJ;
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(input.equals(vo.getName())){
                target.add(vo);
                latitude = Double.parseDouble(vo.getY());
                longitude = Double.parseDouble(vo.getX());
            }
        }

        if(target.size()==0){
            Toast.makeText(mView.getContextView(), "未查询到相关站点", Toast.LENGTH_SHORT);
        }else if(target.size()==1){
            onRequestStationInfo(target);
            mView.showLocation(latitude, longitude);
        }else {
            Toast.makeText(mView.getContextView(), "查询失败", Toast.LENGTH_SHORT);
        }

    }

    //configure the map
    private void initLocation() {
        mLocationClient = new AMapLocationClient(mView.getContextView());
        mLocationClient.setLocationListener(aMapLocation -> {
            double latitude = CommonConstant.LATITUDE_OF_NJ;
            double longitude = CommonConstant.LONGITUDE_OF_NJ;
            if(aMapLocation.getErrorCode() == 0){
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
            }
            mView.showLocation(latitude, longitude);
        });

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setMockEnable(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    //process the data
    private void onRequestStationInfo(List<WaterStationInfoVO> waterStationInfoVOS){
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


}
