package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelHistoricalVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.OrderConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/25
 * @time : 下午1:21
 */

public class WaterLevelPresenter implements WaterLevelContract.Presenter{

    public static final int REAL_TIME = 0;
    public static final int DAY = 1;
    public static final int MONTH = 2;

    private WaterLevelContract.View mView;

    private int mStnId;
    private String[] picUrl = new String[4];
    private String[] picDate = new String[4];
    private String startTime = TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();

    public WaterLevelPresenter(WaterLevelContract.View view, int stnId) {
        this.mView = view;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDefaultWaterLevelData();
        loadCurrentWaterLevelInfo(startTime, endTime);
    }

    @Override
    public void processTab(int index) {
        switch (index){
            case REAL_TIME:
                loadDefaultWaterLevelData();
                break;
            case DAY:
                break;
            case MONTH:
                break;
        }

        if(index == REAL_TIME){
            loadDefaultWaterLevelData();
        }else {
            String startTime;
            String endTime = TimeUtil.getTodayDate();
            if(index == DAY){
                startTime = TimeUtil.getDateBeforeNum(15);
            }else {
                startTime = TimeUtil.getDateBeforeNum(30);
            }
            loadWaterLevelDataByDate(startTime, endTime);
        }
    }

    @Override
    public void loadDefaultWaterLevelData() {
        RetrofitHelper.getWaterInterface().getWaterLevelByNum(mStnId, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WaterLevelVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterLevelVO> waterLevelVOs) {
                        onNetworkRequest(OrderConstant.DESC, waterLevelVOs);
                    }
                });

    }

    @Override
    public void loadWaterLevelDataByDate(String startTime, String endTime) {
        RetrofitHelper.getWaterInterface().getWaterLevelByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WaterLevelVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterLevelVO> waterLevelVOS) {
                        onNetworkRequest(OrderConstant.ASC, waterLevelVOS);
                    }
                });
    }

    private void onNetworkRequest(OrderConstant orderConstant, List<WaterLevelVO> waterLevelVOs){
        List<String> waterLevelDateList = new ArrayList<>();
        List<Float> waterLevelDataList = new ArrayList<>();
        WaterLevelVO waterLevelVO;
        int len = waterLevelVOs.size();
        for(int i=0;i<len;i++){
            if(orderConstant == OrderConstant.ASC){
                waterLevelVO = waterLevelVOs.get(i);
            }else {
                waterLevelVO = waterLevelVOs.get(len-i-1);
            }
            waterLevelDateList.add(waterLevelVO.getC_time());
            waterLevelDataList.add(waterLevelVO.getWaterLevel());
        }
        mView.showWaterLevelInfo(waterLevelDateList, waterLevelDataList);
    }

    @Override
    public void loadCurrentWaterLevelInfo(String startTime, String endTime) {
        RetrofitHelper.getWaterInterface().getCurrentWaterLevelInfo(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WaterLevelHistoricalVO>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WaterLevelHistoricalVO waterLevelHistoricalVO) {
                        String stnName = waterLevelHistoricalVO.getStnName();
                        double lastLevel = waterLevelHistoricalVO.getLastWaterLevel();
                        String lastLevelStr = lastLevel + "m";
                        double minLevel = waterLevelHistoricalVO.getMinLevel();
                        double maxLevel = waterLevelHistoricalVO.getMaxLevel();
                        String rangStr = minLevel+" - "+maxLevel+"m";

                        List<WaterLevelHistoricalVO.PicListBean> picListBeanList = waterLevelHistoricalVO.getPicList();
                        WaterLevelHistoricalVO.PicListBean picListBean;
                        for(int i=0;i<picDate.length;i++){
                            picListBean = picListBeanList.get(i);
                            picDate[i] = picListBean.getDate();
                            picUrl[i] = picListBean.getUrl().replace(" ", "%20");
                        }

                        mView.showCurrentWaterLevelDetailInfo(stnName, lastLevelStr, rangStr, picUrl, picDate);
                    }
                });

    }

    @Override
    public void loadLocationInfo() {
        //TODO 请求接口
        mView.showCurrentLocation(20, 120);
    }
}
