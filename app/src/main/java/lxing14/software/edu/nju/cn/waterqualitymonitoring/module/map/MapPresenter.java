package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/20
 * @time : 下午2:55
 */

public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View mMapView;

    public MapPresenter(@NonNull MapContract.View view){
        mMapView = view;
    }

    @Override
    public void start() {

    }
}
