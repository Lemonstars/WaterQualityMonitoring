package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map.MapInfoWindowAdapter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;

public class WaterQualityFragment extends Fragment implements WaterQualityContract.View, View.OnClickListener{

    private WaterQualityContract.Presenter mPresenter;

    private LineChart mLineChart;
    private ImageView mBig_iv;
    private RecyclerView mType_rv;
    private MapView mMapView;
    private AMap mAMap;
    private WaterQualityRVAdapter mAdapter;
    private ChartMarkerView mChartMarkerView;


    public static WaterQualityFragment generateFragment(){
        return new WaterQualityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_quality, container, false);

        findView(root);

        initRecyclerView();
        configListener();
        configChartMarkerView();
        ChartUtil.configLineChart(mLineChart);

        mPresenter.loadChartDataByDate(TimeUtil.getDateBeforeNum(7), TimeUtil.getTodayDate());
        mPresenter.loadCurrentWaterQualityInfo();
        mPresenter.loadAllStationInfo();

        mMapView.onCreate(savedInstanceState);
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        showCurrentLocation();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void setPresenter(WaterQualityContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showStationLocation(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void showChartUnit(String unit) {
        Description description = mLineChart.getDescription();
        description.setText(unit);
    }


    @Override
    public void configChartMarkerView(String entry, String unit) {
        mChartMarkerView.setEntry(entry);
        mChartMarkerView.setUnit(unit);
    }

    @Override
    public void showWaterQualityChart(List<String> dateList, List<Float> dataList ) {
        IAxisValueFormatter iAxisValueFormatter = new IndexAxisValueFormatter(dateList);
        mLineChart.getXAxis().setValueFormatter(iAxisValueFormatter);
        mChartMarkerView.setIAxisValueFormatter(iAxisValueFormatter);
        mLineChart.setMarker(mChartMarkerView);

        List<Entry> lineEntry = new ArrayList<>();
        int len = dateList.size();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterQuality");
        lineDataSet.setLineWidth(2f);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public void showCurrentWaterQualityInfo(String temperature, double ph, double dissolvedOxygen, double redox,
                                            double transparency, double conductivity, double turbidity, double nh3) {
        WaterQualityTypeVO temperatureVO = new WaterQualityTypeVO(WaterQualityData.TEMPERATURE, temperature);
        WaterQualityTypeVO phVO = new WaterQualityTypeVO(WaterQualityData.PH, String.valueOf(ph));
        WaterQualityTypeVO dissolvedOxygenVO = new WaterQualityTypeVO(WaterQualityData.OXYGEN, String.valueOf(dissolvedOxygen));
        WaterQualityTypeVO redoxVO = new WaterQualityTypeVO(WaterQualityData.OXIDATION, String.valueOf(redox));
        WaterQualityTypeVO transparencyVO = new WaterQualityTypeVO(WaterQualityData.TRANSPANENCY, String.valueOf(transparency));
        WaterQualityTypeVO conductivityVO = new WaterQualityTypeVO(WaterQualityData.ELECTRIC, String.valueOf(conductivity));
        WaterQualityTypeVO turbidityVO = new WaterQualityTypeVO(WaterQualityData.DIRTY, String.valueOf(turbidity));
        WaterQualityTypeVO nh3VO = new WaterQualityTypeVO(WaterQualityData.AMMONIA, String.valueOf(nh3));

        List<WaterQualityTypeVO> data = new ArrayList<>();
        data.add(temperatureVO);
        data.add(phVO);
        data.add(dissolvedOxygenVO);
        data.add(redoxVO);
        data.add(transparencyVO);
        data.add(conductivityVO);
        data.add(turbidityVO);
        data.add(nh3VO);
        mAdapter.setData(data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.big_iv:
                mPresenter.jumpToChartActivity();
                break;
        }

    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //configure the name and the unit
    private void configChartMarkerView(){
        mAMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(), false));
        mChartMarkerView = new ChartMarkerView(getContext(),  "温度:", "°C");
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        AMap aMap = mMapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }

    //find the view
    private void findView(View root){
        mType_rv = root.findViewById(R.id.type_rv);
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mLineChart = root.findViewById(R.id.lineChart);
        mBig_iv = root.findViewById(R.id.big_iv);
    }

    //initialize the recyclerView
    private void initRecyclerView(){
        mAdapter = new WaterQualityRVAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mType_rv.setAdapter(mAdapter);
        mType_rv.setLayoutManager(linearLayoutManager);
    }

    //configure the listener
    private void configListener(){
        mBig_iv.setOnClickListener(this);
    }
}
