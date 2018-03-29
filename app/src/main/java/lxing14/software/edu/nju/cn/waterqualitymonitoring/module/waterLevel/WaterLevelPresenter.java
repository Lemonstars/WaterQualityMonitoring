package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WebSite;
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

    private WaterLevelContract.View mView;

    public WaterLevelPresenter(WaterLevelContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        getDefaultWaterLevelInfo();
        getCurrentWaterLevelInfo();
    }

    @Override
    public void getDefaultWaterLevelInfo() {
        RetrofitHelper.getWaterInterface().getWaterLevelInfo(1, 30)
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
                        List<String> waterLevelDateList = new ArrayList<>();
                        List<Float> waterLevelDataList = new ArrayList<>();
                        WaterLevelVO waterLevelVO;
                        for(int i=0;i<waterLevelVOs.size();i+=5){
                            waterLevelVO = waterLevelVOs.get(i);
                            waterLevelDateList.add(waterLevelVO.getC_time());
                            waterLevelDataList.add(waterLevelVO.getWaterLevel());
                        }
                        mView.showWaterLevelInfo(waterLevelDateList, waterLevelDataList);
                    }
                });

    }

    @Override
    public void getCurrentWaterLevelInfo() {
        RetrofitHelper.getWaterInterface().getCurrentWaterLevelInfo(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WaterLevelVO>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WaterLevelVO waterLevelVO) {
                        String picUrl = WebSite.PIC_Prefix + waterLevelVO.getPicPath();
                        String picUrlEncode = picUrl.replace(" ", "%20");
                        String currentWaterLevel = waterLevelVO.getWaterLevel() + " mm";
                        //TODO 历史数据如何获取
                        String historicalWaterLevel = "1-2 mm";
                        String photoBy = waterLevelVO.getC_time();

                        mView.showCurrentWaterLevelDetailInfo(picUrlEncode, currentWaterLevel, historicalWaterLevel, photoBy);
                    }
                });
    }
}
