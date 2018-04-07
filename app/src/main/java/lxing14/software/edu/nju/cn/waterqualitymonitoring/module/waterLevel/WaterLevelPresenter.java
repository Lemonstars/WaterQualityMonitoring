package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelHistoricalVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.OrderConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterTypeEnum;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/25
 * @time : 下午1:21
 */

public class WaterLevelPresenter implements WaterLevelContract.Presenter{

    private WaterLevelContract.View mView;

    private int mStnId;
    private String[] picUrl = new String[4];
    private String[] picDate = new String[4];
    private String startTime = TimeUtil.getDateBeforeNum(7);
    private String endTime = TimeUtil.getTodayDate();
    private ArrayList<String> waterLevelDateList = new ArrayList<>();
    private ArrayList<Float> waterLevelDataList = new ArrayList<>();

    public WaterLevelPresenter(WaterLevelContract.View view, int stnId) {
        this.mView = view;
        this.mStnId = stnId;
        mView.setPresenter(this);
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
        loadWaterLevelDataByDate(startTime, endTime);

    }

    @Override
    public void loadWaterLevelDataByDate(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        RetrofitHelper.getWaterInterface().getWaterLevelByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterLevelVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterLevelVO> waterLevelVOS) {
                        waterLevelDateList.clear();
                        waterLevelDataList.clear();
                        WaterLevelVO waterLevelVO;
                        int len = waterLevelVOS.size();
                        for(int i=0;i<len;i++){
                            waterLevelVO = waterLevelVOS.get(i);
                            waterLevelDateList.add(waterLevelVO.getC_time());
                            waterLevelDataList.add(waterLevelVO.getWaterLevel());
                        }
                        mView.showWaterLevelChartData(waterLevelDateList, waterLevelDataList);
                    }
                });
    }

    @Override
    public void loadCurrentWaterLevelInfo() {
        RetrofitHelper.getWaterInterface().getCurrentWaterLevelInfo(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WaterLevelHistoricalVO>(mView.getContextView()) {
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
    public void jumpToChartActivity() {
        ArrayList<String> dataList = new ArrayList<>(waterLevelDataList.size());
        for(Float num: waterLevelDataList){
            dataList.add(String.valueOf(num));
        }

        Context context = mView.getContextView();
        Intent intent = ChartActivity.generateIntent(context, "水位", "m",startTime, endTime, waterLevelDateList, dataList);
        context.startActivity(intent);
    }

}
