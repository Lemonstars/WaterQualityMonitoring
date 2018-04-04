package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class MapFragment extends Fragment implements MapContract.View, View.OnClickListener{

    private MapContract.Presenter mPresenter;

    private MapView mMapView;
    private AMap mAMap;
    private TextView mStandard_tv;
    private TextView mSatellite_tv;
    private boolean mIsStandard = true;

    public static MapFragment generateFragment(){
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        findView(root);
        configListener();
        mAMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getContext()));

        mPresenter.loadAllWaterTypeInfo();

        mMapView.onCreate(savedInstanceState);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void showInitPoint(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showCurrentLocation(double latitude, double longitude) {
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 10f));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.standard_tv:
                showMapType(true);
                break;
            case R.id.satellite_tv:
                showMapType(false);
                break;
        }
    }

    @Override
    public Context getContextView() {
        return getActivity();
    }

    //show the selected type of the map
    private void showMapType(boolean isStandard){
        if(isStandard==mIsStandard){
            return;
        }
        this.mIsStandard = isStandard;

        mAMap.setMapType(isStandard? AMap.MAP_TYPE_NORMAL:AMap.MAP_TYPE_SATELLITE);
        mStandard_tv.setTextColor(getResources().getColor(isStandard? R.color.skyBlue:R.color.black));
        mSatellite_tv.setTextColor(getResources().getColor(isStandard? R.color.black:R.color.skyBlue));
    }

    //configure the listener
    private void configListener(){
        mStandard_tv.setOnClickListener(this);
        mSatellite_tv.setOnClickListener(this);
    }

    //find the view
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mStandard_tv = root.findViewById(R.id.standard_tv);
        mSatellite_tv = root.findViewById(R.id.satellite_tv);
    }
}
