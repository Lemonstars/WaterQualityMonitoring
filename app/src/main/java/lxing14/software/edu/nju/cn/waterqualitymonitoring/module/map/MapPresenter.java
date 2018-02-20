package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.support.annotation.NonNull;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterMapInfoVO;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {
        loadInitPointInfo();
    }

    private void loadInitPointInfo(){
        RetrofitHelper.getWaterInterface().getMapInfo(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WaterMapInfoVO>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WaterMapInfoVO> waterMapInfoVOs) {
                        LatLng latLng;
                        ArrayList<MarkerOptions> markerOptionsArrayList =
                                new ArrayList<MarkerOptions>(waterMapInfoVOs.size());
                        for(WaterMapInfoVO vo: waterMapInfoVOs){
                            double x = Double.parseDouble(vo.getX());
                            double y = Double.parseDouble(vo.getY());

                            latLng = new LatLng(y, x);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng).title(vo.getStnName());
                            markerOptionsArrayList.add(markerOptions);
                        }

                        mMapView.showInitPoint(markerOptionsArrayList);
                    }
                });
    }

}
