package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;

public class WaterQualityFragment extends Fragment implements WaterQualityContract.View, View.OnClickListener{

    private WaterQualityContract.Presenter mPresenter;

    private LineChart mLineChart;
    private RecyclerView mType_rv;
    private MapView mMapView;
    private WaterQualityRVAdapter mAdapter;

    private TextView[] mTab_tv;
    //TODO 等待确认
    private String[] mChartUnit = new String[]{"°C", "S/m", "", "mg/L", "mV", "NTU", "%", "mg/L"};

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
        ChartUtil.configLineChart(mLineChart);

        mMapView.onCreate(savedInstanceState);
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        showCurrentLocation();

        mPresenter.start();
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
    public void showTabSelected(int index) {
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(index==i?R.color.colorPrimary:R.color.black));
        }
    }

    @Override
    public void showChartUnit(int index) {
        Description description = mLineChart.getDescription();
        description.setText(mChartUnit[index]);
    }

    @Override
    public void showWaterQualityChart(List<String> dateList, List<Float> dataList ) {
        mLineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dateList));

        List<Entry> lineEntry = new ArrayList<>();
        int len = dateList.size();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterQuality");
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
        int index =0;
        switch (view.getId()){
            case R.id.temperature_tv:
                index = WaterQualityData.TEMPERATURE;
                break;
            case R.id.conductivity_tv:
                index = WaterQualityData.ELECTRIC;
                break;
            case R.id.ph_tv:
                index = WaterQualityData.PH;
                break;
            case R.id.dissolvedOxygen_tv:
                index = WaterQualityData.OXYGEN;
                break;
            case R.id.redox_tv:
                index = WaterQualityData.OXIDATION;
                break;
            case R.id.turbidity_tv:
                index = WaterQualityData.DIRTY;
                break;
            case R.id.transparency_tv:
                index = WaterQualityData.TRANSPANENCY;
                break;
            case R.id.nh3_tv:
                index = WaterQualityData.AMMONIA;
                break;
        }

        mPresenter.loadChartDataByType(index);
    }

    @Override
    public Context getContextView() {
        return getContext();
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
        mLineChart = root.findViewById(R.id.lineChart);

        TextView temperature_tv = root.findViewById(R.id.temperature_tv);
        TextView conductivity_tv = root.findViewById(R.id.conductivity_tv);
        TextView ph_tv = root.findViewById(R.id.ph_tv);
        TextView dissolvedOxygen_tv = root.findViewById(R.id.dissolvedOxygen_tv);
        TextView redox_tv = root.findViewById(R.id.redox_tv);
        TextView turbidity_tv = root.findViewById(R.id.turbidity_tv);
        TextView transparency_tv = root.findViewById(R.id.transparency_tv);
        TextView nh3_tv = root.findViewById(R.id.nh3_tv);
        mTab_tv = new TextView[]{temperature_tv, conductivity_tv, ph_tv,
                dissolvedOxygen_tv, redox_tv, turbidity_tv, transparency_tv, nh3_tv};
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
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(this);
        }
    }

}
