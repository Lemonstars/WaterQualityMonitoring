package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingByDateVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingPicVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingVO;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午2:06
 */

public class WaterFloatingPresenter implements WaterFloatingContract.IPresenter {

    private WaterFloatingContract.IView mView;
    private int mStnId;

    public WaterFloatingPresenter(WaterFloatingContract.IView mView, int stnId) {
        this.mView = mView;
        this.mStnId = stnId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadWaterFloatingChartByDate(String startTime, String endTime) {
        RetrofitHelper.getWaterFloatingInterface().getFloatingInfoByDate(mStnId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFloatingByDateVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFloatingByDateVO> waterFloatingByDateVOS) {
                        int len = waterFloatingByDateVOS.size();
                        List<String> dateList = new ArrayList<>(len);
                        List<Integer> dataList = new ArrayList<>(len);
                        for(WaterFloatingByDateVO vo: waterFloatingByDateVOS){
                            dateList.add(vo.getDays());
                            dataList.add(vo.getNums());
                        }
                        mView.showBarChart(dateList, dataList);
                    }
                });
    }

    @Override
    public void loadWaterFloatingPicURl() {
        RetrofitHelper.getWaterFloatingInterface().getFloatingInfoPic(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterFloatingPicVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterFloatingPicVO> waterFloatingPicVOS) {
                        String url1 = waterFloatingPicVOS.get(0).getFilePathResult();
                        String url2 = waterFloatingPicVOS.get(1).getFilePathResult();
                        String url3 = waterFloatingPicVOS.get(2).getFilePathResult();
                        mView.showFloatingPic(url1, url2, url3);
                    }
                });
    }
}
