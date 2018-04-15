package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.MapMarkerConfig;
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

    private List<WaterStationInfoVO> mWaterStationInfoList;
    private List<String> completeDataList;
    private MapMarkerConfig mapMarkerConfig = new MapMarkerConfig() {
        @Override
        public void showStationLocation(ArrayList<MarkerOptions> list) {
            mView.showStationLocation(list);
        }
    };

    public MapPresenter(@NonNull MapContract.View view){
        mView = view;
        mView.setPresenter(this);
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
                        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);

                        completeDataList = new ArrayList<>(waterStationInfoVOS.size());
                        WaterStationInfoVO stationInfoVO;
                        for(int i=0;i<waterStationInfoVOS.size();i++){
                            stationInfoVO = waterStationInfoVOS.get(i);
                            completeDataList.add(stationInfoVO.getName());
                        }
                        mView.setCompleteAdapter(completeDataList);
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
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadWaterFlowInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWaterFlow()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadWaterQualityInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWaterQuality()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadFloatingMaterialInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasFloatingMaterial()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadUnmannedShipInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasUnmannedShip()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadHisRecordInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasHistoryRecord()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadWaveInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWave()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
    }

    @Override
    public void loadWeatherInfo() {
        List<WaterStationInfoVO> waterStationInfoVOS = new ArrayList<>();
        for(WaterStationInfoVO vo: mWaterStationInfoList){
            if(vo.isHasWeather()){
                waterStationInfoVOS.add(vo);
            }
        }
        mapMarkerConfig.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
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
            mapMarkerConfig.onRequestStationInfo(mView.getContextView(), target);
            mView.showLocation(latitude, longitude);
        }else {
            Toast.makeText(mView.getContextView(), "查询失败", Toast.LENGTH_SHORT);
        }

    }


}
