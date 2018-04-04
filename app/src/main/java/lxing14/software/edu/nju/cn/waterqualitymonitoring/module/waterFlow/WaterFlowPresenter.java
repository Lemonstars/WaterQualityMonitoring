package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowCameraInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVideoUrlVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.OrderConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.Subscriber;
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
    private String[] videoUrl;
    private String[] cameraPic;
    private double[] cameraWaterFlow;
    private double[] cameraWaterSpeed;
    private String mCollectTime;

    public WaterFlowPresenter(WaterFlowContract.View mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        videoUrl = new String[5];
        cameraPic = new String[5];
        cameraWaterFlow = new double[5];
        cameraWaterSpeed = new double[5];
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDefaultWaterFlowData();
    }

    @Override
    public void loadDefaultWaterFlowData() {
        RetrofitHelper.getWaterFlowInterface().getLatestWaterFlow(mStnId, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFlowVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowVO> waterFlowVOList) {
                        onNetworkRequest(OrderConstant.DESC, waterFlowVOList);
                    }
                });
    }

    @Override
    public void loadWaterFlowDataByDate(String startTime, String endTime) {
        RetrofitHelper.getWaterFlowInterface().getWaterFlowByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFlowVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowVO> waterFlowVOList) {
                        onNetworkRequest(OrderConstant.ASC, waterFlowVOList);
                    }
                });
    }

    @Override
    public void loadCameraInfoFromNetwork() {
        RetrofitHelper.getWaterFlowInterface().getCameraInfo(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFlowCameraInfoVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowCameraInfoVO> waterFlowCameraInfoVOS) {
                        WaterFlowCameraInfoVO vo = waterFlowCameraInfoVOS.get(0);
                        cameraPic[0]=vo.getFilePath1();
                        cameraPic[1]=vo.getFilePath2();
                        cameraPic[2]=vo.getFilePath3();
                        cameraPic[3]=vo.getFilePath4();
                        cameraPic[4]=vo.getFilePath5();

                        cameraWaterFlow[0]=vo.getWaterFlow1();
                        cameraWaterFlow[1]=vo.getWaterFlow2();
                        cameraWaterFlow[2]=vo.getWaterFlow3();
                        cameraWaterFlow[3]=vo.getWaterFlow4();
                        cameraWaterFlow[4]=vo.getWaterFlow5();

                        cameraWaterSpeed[0]=vo.getWaterSpeed1();
                        cameraWaterSpeed[1]=vo.getWaterSpeed2();
                        cameraWaterSpeed[2]=vo.getWaterSpeed3();
                        cameraWaterSpeed[3]=vo.getWaterSpeed4();
                        cameraWaterSpeed[4]=vo.getWaterSpeed5();

                        mCollectTime = vo.getCollectionTime();

                        loadCameraInfo(0);
                    }
                });
    }

    @Override
    public void loadCameraInfo(int index) {
        mView.showCameraInfo(index, mCollectTime, cameraPic[index], cameraWaterFlow[index], cameraWaterSpeed[index]);
    }

    @Override
    public void loadWaterFlowVideoUrl() {
        RetrofitHelper.getWaterFlowInterface().getVideoUrl(mStnId)
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<WaterFlowVideoUrlVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFlowVideoUrlVO> waterFlowVideoUrlVOList) {
                        int size = waterFlowVideoUrlVOList.size();
                        if(size != 5){
                            throw new IllegalArgumentException();
                        }else {
                            WaterFlowVideoUrlVO waterFlowVideoUrlVO;
                            for(int i=0;i<size;i++){
                                waterFlowVideoUrlVO = waterFlowVideoUrlVOList.get(i);
                                videoUrl[i] = waterFlowVideoUrlVO.getUrl();
                            }
                        }
                    }
                });
    }

    @Override
    public void processTab(int index) {
        if(index == CommonConstant.REAL_TIME){
            loadDefaultWaterFlowData();
        }else {
            String startTime;
            String endTime = TimeUtil.getTodayDate();
            if(index == CommonConstant.DAY){
                startTime = TimeUtil.getDateBeforeNum(15);
            }else {
                startTime = TimeUtil.getDateBeforeNum(30);
            }
            loadWaterFlowDataByDate(startTime, endTime);
        }
    }

    // process the data
    private void onNetworkRequest(OrderConstant orderConstant, List<WaterFlowVO> waterFlowVOList){
        int len = waterFlowVOList.size();
        List<String> dateList = new ArrayList<>(len);
        List<Float> dataList = new ArrayList<>(len);

        WaterFlowVO waterFlowVO;
        for(int i=0;i<len;i++){
            if(orderConstant == OrderConstant.ASC){
                waterFlowVO = waterFlowVOList.get(i);
            }else {
                waterFlowVO = waterFlowVOList.get(len-i-1);
            }
            dateList.add(waterFlowVO.getCollectionTime());
            dataList.add((float)(waterFlowVO.getAvgFlow()));
        }

        mView.showWaterFlowChartData(dateList, dataList);
    }


}
