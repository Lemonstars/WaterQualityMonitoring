package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip.UnMannedShipActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating.WaterFloatingActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow.WaterFlowActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel.WaterLevelActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality.WaterQualityActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.WaterInfoView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/24
 * @time : 下午12:56
 */

public class MapInfoWindowAdapter implements AMap.InfoWindowAdapter {

    private Context context;
    private LinearLayout infoWindow = null;

    public MapInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        MarkerOptions markerOptions = marker.getOptions();
        String stnCode = markerOptions.getSnippet();

        int stn = Integer.parseInt(stnCode);

        if (infoWindow == null) {
            infoWindow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bg_info_window, null);

            RetrofitHelper.getWaterStationInterface().getStationInfo(stn)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WaterStationInfoVO>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(WaterStationInfoVO waterStationInfoVO) {
                            WaterInfoView waterInfoView;
                            if(true){
                                waterInfoView = new WaterInfoView(context, context.getString(R.string.waterLevel), "1.0mm");
                                waterInfoView.setOnClickListener(v -> context.startActivity(WaterLevelActivity.generateIntent(context, 30,100)));
                                infoWindow.addView(waterInfoView);
                            }

                            if(true){
                                waterInfoView = new WaterInfoView(context, context.getString(R.string.waterQuality), "1.0mm");
                                waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterQualityActivity.class)));
                                infoWindow.addView(waterInfoView);
                            }

                            if(true){
                                waterInfoView = new WaterInfoView(context, context.getString(R.string.waterFlow), "10 m/s");
                                waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFlowActivity.class)));
                                infoWindow.addView(waterInfoView);
                            }

                            if(true){
                                waterInfoView = new WaterInfoView(context, context.getString(R.string.floatingMaterial), "floating");
                                waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFloatingActivity.class)));
                                infoWindow.addView(waterInfoView);
                            }

                            if(true){
                                waterInfoView = new WaterInfoView(context, context.getString(R.string.unmannedShip), "unmannedShip");
                                waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, UnMannedShipActivity.class)));
                                infoWindow.addView(waterInfoView);
                            }

                        }
                    });
        }

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
