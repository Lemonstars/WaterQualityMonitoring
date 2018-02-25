package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

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

    }
}
