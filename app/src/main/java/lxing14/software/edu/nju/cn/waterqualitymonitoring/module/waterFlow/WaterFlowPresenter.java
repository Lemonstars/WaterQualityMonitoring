package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/15
 * @time : 下午8:32
 */

public class WaterFlowPresenter implements WaterFlowContract.Presenter {

    private WaterFlowContract.View mView;

    public WaterFlowPresenter(WaterFlowContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showCameraChoiceView(5);
        mView.showRealTimeChart();
    }



}
