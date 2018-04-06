package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/26
 * @time : 下午3:48
 */

public class UnMannedShipPresenter implements UnMannedShipContract.Presenter {

    private UnMannedShipContract.View mView;

    public UnMannedShipPresenter(UnMannedShipContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

}
