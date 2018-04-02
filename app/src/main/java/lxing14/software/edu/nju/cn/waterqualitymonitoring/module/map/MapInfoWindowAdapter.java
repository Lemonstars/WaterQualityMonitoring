package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

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
    private int[] type;

    public MapInfoWindowAdapter(Context context) {
        this.context = context;

        type = new int[]{R.string.waterLevel, R.string.waterQuality,
                R.string.waterFlow, R.string.floatingMaterial, R.string.unmannedShip};
    }

    @Override
    public View getInfoWindow(Marker marker) {
        MarkerOptions markerOptions = marker.getOptions();
        String extra = markerOptions.getSnippet();
        String[] data = extra.split("\\s+");

        int stn = Integer.parseInt(data[0]);
        String name = data[1];
        boolean hasWaterLevel = Integer.parseInt(data[2])==1;
        boolean hasWaterQuality = Integer.parseInt(data[3])==1;
        boolean hasWaterFlow = Integer.parseInt(data[4])==1;
        boolean hasFloating = Integer.parseInt(data[5])==1;
        boolean hasBoat = Integer.parseInt(data[6])==1;
        boolean[] typeExisted = new boolean[]{hasWaterLevel, hasWaterQuality, hasWaterFlow, hasFloating, hasBoat};

        LinearLayout infoWindow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bg_info_window, null);
        TextView name_tv = infoWindow.findViewById(R.id.name_tv);
        name_tv.setText(name);

        WaterInfoView waterInfoView;
        for(int i=0;i<typeExisted.length;i++){
            if(typeExisted[i]){
                waterInfoView = new WaterInfoView(context, context.getString(type[i]));
                infoWindow.addView(waterInfoView);
                switch (i){
                    case 0:
                        waterInfoView.setOnClickListener(v -> context.startActivity(WaterLevelActivity.generateIntent(context, 30,100)));
                        break;
                    case 1:
                        waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterQualityActivity.class)));
                        break;
                    case 2:
                        waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFlowActivity.class)));
                        break;
                    case 3:
                        waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, WaterFloatingActivity.class)));
                        break;
                    case 4:
                        waterInfoView.setOnClickListener(v -> context.startActivity(new Intent(context, UnMannedShipActivity.class)));
                        break;
                }

            }

        }


        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
