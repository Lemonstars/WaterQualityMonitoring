package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.CameraVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowCameraInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVideoUrlVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/15
 * @time : 下午8:32
 */

public class WaterFlowPresenter implements WaterFlowContract.Presenter {

    private WaterFlowContract.View mView;
    private int mStnId;
    private List<String> videoUrlList;
    private List<String> cameraPicList;
    private List<Double> cameraWaterFlowList;
    private List<Double> cameraWaterSpeedList;
    private String mCollectTime;

    private String startTime = TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Float> dataList = new ArrayList<>();

    public WaterFlowPresenter(WaterFlowContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        this.videoUrlList = new ArrayList<>();
        this.cameraPicList = new ArrayList<>();
        this.cameraWaterFlowList = new ArrayList<>();
        this.cameraWaterSpeedList = new ArrayList<>();
        mView.setPresenter(this);
    }


    @Override
    public void loadWaterFlowDataByDate(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        RetrofitHelper.getWaterFlowInterface().getWaterFlowByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFlowVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowVO> waterFlowVOList) {
                        dateList.clear();
                        dataList.clear();
                        int len = waterFlowVOList.size();
                        WaterFlowVO waterFlowVO;
                        for(int i=0;i<len;i++){
                            waterFlowVO = waterFlowVOList.get(i);
                            dateList.add(waterFlowVO.getCollectionTime());
                            dataList.add((float)(waterFlowVO.getAvgFlow()));
                        }

                        mView.showWaterFlowChartData(dateList, dataList);
                    }
                });
    }

    @Override
    public void loadCameraInfoFromNetwork() {
        RetrofitHelper.getWaterFlowInterface().getCameraInfo(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WaterFlowCameraInfoVO>(mView.getContextView()) {
                    @Override
                    public void onNext(WaterFlowCameraInfoVO waterFlowCameraInfoVO) {
                        mCollectTime = waterFlowCameraInfoVO.getCollectionTime();
                        List<CameraVO> cameraVOList = waterFlowCameraInfoVO.getList();
                        for(CameraVO vo: cameraVOList){
                            cameraPicList.add(vo.getFilePath());
                            cameraWaterFlowList.add(vo.getFlow());
                            cameraWaterSpeedList.add(vo.getSpeed());
                        }

                        loadCameraInfo(0);
                    }
                });
    }

    @Override
    public void loadCameraInfo(int i) {
        mView.showCameraInfo(i, mCollectTime, cameraPicList.get(i), cameraWaterFlowList.get(i), cameraWaterSpeedList.get(i));
    }

    @Override
    public void loadWaterFlowVideoUrl() {
        RetrofitHelper.getWaterFlowInterface().getVideoUrl(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFlowVideoUrlVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowVideoUrlVO> waterFlowVideoUrlVOList) {
                        if(waterFlowVideoUrlVOList==null || waterFlowVideoUrlVOList.size()==0){
                            mView.hideCameraLayout();
                            return;
                        }

                         for(WaterFlowVideoUrlVO vo: waterFlowVideoUrlVOList){
                             videoUrlList.add(vo.getUrl());
                         }
                         mView.showCameraLayout(videoUrlList.size());
                    }
                });
    }

    @Override
    public void processTab(int index) {
        endTime = TimeUtil.getTodayDate();
        switch (index){
            case CommonConstant.ONE_WEEK:
                startTime = TimeUtil.getDateBeforeNum(7);
                break;
            case CommonConstant.ONE_MONTH:
                startTime = TimeUtil.getDateBeforeNum(30);
                break;
            case CommonConstant.THREE_MONTH:
                startTime = TimeUtil.getDateBeforeNum(90);
                break;
        }
        loadWaterFlowDataByDate(startTime, endTime);

    }

    @Override
    public void jumpToChartActivity() {
        ArrayList<String> dataStrList = new ArrayList<>(dataList.size());
        for(Float num: dataList){
            dataStrList.add(String.valueOf(num));
        }

        Context context = mView.getContextView();
        Intent intent = ChartActivity.generateIntent(context, "流量", "m/s",startTime, endTime, dateList, dataStrList);
        context.startActivity(intent);
    }

    @Override
    public void loadAllStationInfo() {
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mView.getContextView()) {
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
                            sb.append(vo.isHasHistoryRecord()? 1:0); // record
                            sb.append(' ');
                            sb.append(y);
                            sb.append(' ');
                            sb.append(x);

                            markerOptions.position(latLng).snippet(sb.toString());
                            markerOptionsArrayList.add(markerOptions);
                        }

                        mView.showStationLocation(markerOptionsArrayList);
                    }
                });
    }
}
