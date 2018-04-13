package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record.RecordActivity;
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

    private Activity context;
    private boolean isFrontPage;
    private int[] type;

    public MapInfoWindowAdapter(Activity context, boolean isFrontPage) {
        this.context = context;
        this.isFrontPage = isFrontPage;

        type = new int[]{R.string.waterLevel, R.string.waterQuality, R.string.waterFlow, R.string.floatingMaterial, R.string.unmannedShip};
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
        float latitude = Float.parseFloat(data[7]);
        float longitude = Float.parseFloat(data[8]);

        LinearLayout infoWindow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bg_info_window, null);
        TextView name_tv = infoWindow.findViewById(R.id.name_tv);
        ImageView hide_iv = infoWindow.findViewById(R.id.hide_iv);
        name_tv.setText(name);
        hide_iv.setOnClickListener(v -> marker.hideInfoWindow());

        WaterInfoView waterInfoView;
        for(int i=0;i<typeExisted.length;i++){
            if(typeExisted[i]){
                waterInfoView = new WaterInfoView(context, context.getString(type[i]));
                infoWindow.addView(waterInfoView);
                Intent intent = generateIntent(i, stn);
                waterInfoView.setOnClickListener(v -> {
                    context.startActivity(intent);
                    marker.hideInfoWindow();
                    if(!isFrontPage){
                        context.finish();
                    }
                    SharedPreferences sharedPreferences =
                            context.getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat(SharePreferencesConstant.LATITUDE, latitude);
                    editor.putFloat(SharePreferencesConstant.LONGITUDE, longitude);
                    editor.putString(SharePreferencesConstant.STATION_NAME, name);
                    editor.apply();
                });
            }
        }

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private Intent generateIntent(int index, int stnId){
        Intent intent;
        switch (index){
            case 0:
                intent = WaterLevelActivity.generateIntent(context, stnId);
                break;
            case 1:
                intent = WaterQualityActivity.generateIntent(context, stnId);
                break;
            case 2:
                intent = WaterFlowActivity.generateIntent(context, stnId);
                break;
            case 3:
                intent = WaterFloatingActivity.generateIntent(context, stnId);
                break;
            case 4:
                intent = RecordActivity.generateIntent(context, stnId);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return intent;
    }

}
