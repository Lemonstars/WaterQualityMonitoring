package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午2:06
 */

public class WaterFloatingPresenter implements WaterFloatingContract.IPresenter {

    private WaterFloatingContract.IView mView;

    public WaterFloatingPresenter(WaterFloatingContract.IView mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showBarChart();
    }
}
