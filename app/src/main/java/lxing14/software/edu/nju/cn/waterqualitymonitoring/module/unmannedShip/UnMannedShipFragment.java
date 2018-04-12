package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.View{

    private UnMannedShipContract.Presenter mPresenter;

    private MapView mMapView;
    private AMap mAMap;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);

        findView(root);

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
    public void showCenterPoint(float latitude, float longitude) {
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 50f));
    }

    @Override
    public void addBoatLocation(List<LatLng> points) {
        mAMap.clear();
        mAMap.addPolyline(new PolylineOptions().addAll(points).width(8).color(R.color.skyBlue));
        LatLng currentLatLng = points.get(points.size()-1);

        ArrayList<MarkerOptions>  markerOptionList = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_moving_boat));
        markerOptionList.add(markerOptions);
        mAMap.addMarkers(markerOptionList, false);
    }

    @Override
    public Context getContextView() {
        return getContext();
    }


    //find the view
    private void findView(android.view.View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
    }

}
