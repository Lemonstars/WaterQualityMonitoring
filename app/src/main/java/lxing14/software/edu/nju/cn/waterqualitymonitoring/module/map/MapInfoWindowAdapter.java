package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel.WaterLevelActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality.WaterQualityActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.WaterInfoView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/24
 * @time : 下午12:56
 */

public class MapInfoWindowAdapter implements AMap.InfoWindowAdapter{

    private Context context;
    private LinearLayout infoWindow = null;

    public MapInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        if(infoWindow == null) {
            infoWindow = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.bg_info_window, null);

            WaterInfoView view1 = new WaterInfoView(context, context.getString(R.string.waterLevel), "1.0mm");
            view1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, WaterLevelActivity.class));
                }
            });
            WaterInfoView view2 = new WaterInfoView(context, context.getString(R.string.waterQuality), "1.0mm");
            view2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, WaterQualityActivity.class));
                }
            });
            infoWindow.addView(view1);
            infoWindow.addView(view2);



        }
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
