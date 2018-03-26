package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午3:48
 */

public class UnMannedShipPresenter implements UnMannedShipContract.IPresenter {

    private UnMannedShipContract.IView mView;

    public UnMannedShipPresenter(UnMannedShipContract.IView mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
