package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.View{

    private UnMannedShipContract.Presenter mPresenter;

    private MapView mMapView;
    private AMap mAMap;
    private TextView time_tv;
    private TextView tPh_tv;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);
        findView(root);
        configMapView();
        mMapView.onCreate(savedInstanceState);
        return root;
    }

    @Override
    public void setPresenter(UnMannedShipContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        mPresenter.loadBoatCurrentLocation();
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showWaterQualityNum(String date, String tPh) {
        time_tv.setText(date);
        tPh_tv.setText(tPh);
    }

    @Override
    public void addBoatRealTimeLocation(List<LatLng> points) {
        mAMap.clear();
        mAMap.addPolyline(new PolylineOptions().addAll(points).width(8).color(R.color.lightRed));
        LatLng currentLatLng = points.get(points.size()-1);

        ArrayList<MarkerOptions>  markerOptionList = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_moving_boat));
        markerOptionList.add(markerOptions);
        mAMap.addMarkers(markerOptionList, false);
    }

    @Override
    public void addBoatHistoryLocation(List<LatLng> points) {
        LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 1));
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(mAMap);
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.ic_moving_boat));
        smoothMarker.setPoints(points);
        smoothMarker.setTotalDuration(20);
        smoothMarker.startSmoothMove();
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //configure the map view
    private void configMapView(){
        mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);
    }

    //find the view
    private void findView(android.view.View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        time_tv = root.findViewById(R.id.time_tv);
        tPh_tv = root.findViewById(R.id.tPh_tv);
    }

}
