package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/15
 * @time : 下午3:59
 */

public abstract class MapMarkerConfig {

    public void onRequestStationInfo(Context context, List<WaterStationInfoVO> waterStationInfoVOS){
        LatLng latLng;
        Resources resources = context.getResources();
        ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>(waterStationInfoVOS.size());
        for(WaterStationInfoVO vo: waterStationInfoVOS){
            double x = Double.parseDouble(vo.getX());
            double y = Double.parseDouble(vo.getY());

            latLng = new LatLng(y, x);
            MarkerOptions markerOptions = new MarkerOptions();
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(vo.getId()));
            sb.append(' ');
            sb.append(vo.getName());
            sb.append(' ');
            sb.append(vo.isHasWaterLevel()? 1:0); // water level
            sb.append(' ');
            sb.append(vo.isHasWaterQuality()? 1:0); // water quality
            sb.append(' ');
            sb.append(vo.isHasWaterFlow()? 1:0); // water flow
            sb.append(' ');
            sb.append(vo.isHasFloatingMaterial()? 1:0); // floating
            sb.append(' ');
            sb.append(vo.isHasUnmannedShip()? 1:0); // boat
            sb.append(' ');
            sb.append(vo.isHasHistoryRecord()? 1:0); // record
            sb.append(' ');
            sb.append(vo.isHasWave()? 1:0); // wave
            sb.append(' ');
            sb.append(vo.isHasWeather()? 1:0); // weather
            sb.append(' ');
            sb.append(y);
            sb.append(' ');
            sb.append(x);

            markerOptions.position(latLng).snippet(sb.toString());
            BitmapDescriptor targetIcon;
            if(vo.isHasHistoryRecord()){
                targetIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources, R.drawable.ic_location_blue));
            }else{
                targetIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources, R.drawable.ic_location_red));
            }
            markerOptions.icon(targetIcon);
            markerOptionsArrayList.add(markerOptions);
        }

        showStationLocation(markerOptionsArrayList);
    }

    public abstract void showStationLocation(ArrayList<MarkerOptions> list);

}
