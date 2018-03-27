package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午7:22
 */

public class WaterQualityPresenter implements WaterQualityContract.Presenter {

    private WaterQualityContract.View mView;

    public WaterQualityPresenter(WaterQualityContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showWaterQualityChart();
    }
}
