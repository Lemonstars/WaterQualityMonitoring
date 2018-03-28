package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/28
 * @time : 下午9:13
 */

public class ChartPresenter implements ChartContract.Presenter {

    private ChartContract.View mView;

    public ChartPresenter(ChartContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showChart();
    }
}
