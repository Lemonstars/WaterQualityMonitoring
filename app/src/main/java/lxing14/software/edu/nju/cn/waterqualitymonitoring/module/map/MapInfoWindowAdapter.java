package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip.UnMannedShipActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating.WaterFloatingActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow.WaterFlowActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel.WaterLevelActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality.WaterQualityActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.WaterInfoView;

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
        if (infoWindow == null) {
            infoWindow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bg_info_window, null);

            //TODO 如何传递真实值
            WaterInfoView view1 = new WaterInfoView(context, context.getString(R.string.waterLevel), "1.0mm");
            view1.setOnClickListener(v -> context.startActivity(WaterLevelActivity.generateIntent(context, 30,100)));
            WaterInfoView view2 = new WaterInfoView(context, context.getString(R.string.waterQuality), "1.0mm");
            view2.setOnClickListener(v -> context.startActivity(new Intent(context, WaterQualityActivity.class)));
            WaterInfoView view3 = new WaterInfoView(context, context.getString(R.string.waterFlow), "10 m/s");
            view3.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFlowActivity.class)));
            WaterInfoView view4 = new WaterInfoView(context, context.getString(R.string.floatingMaterial), "floating");
            view4.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFloatingActivity.class)));
            WaterInfoView view5 = new WaterInfoView(context, context.getString(R.string.unmannedShip), "unmannedShip");
            view5.setOnClickListener(v -> context.startActivity(new Intent(context, UnMannedShipActivity.class)));

            infoWindow.addView(view1);
            infoWindow.addView(view2);
            infoWindow.addView(view3);
            infoWindow.addView(view4);
            infoWindow.addView(view5);
        }
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
