package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;

public class WaterQualityFragment extends Fragment implements WaterQualityContract.View, View.OnClickListener{

    private WaterQualityContract.Presenter mPresenter;

    private LineChart mLineChart;
    private RecyclerView mType_rv;
    private MapView mMapView;
    private WaterQualityRVAdapter mAdapter;

    private TextView mtemperature_tv;
    private TextView mPh_tv;
    private TextView mDissolvedOxygen_tv;
    private TextView mRedox_tv;
    private TextView mTransparency_tv;
    private TextView mConductivity_tv;
    private TextView mTurbidity_tv;
    private TextView mNh3_tv;
    private TextView[] mTab_tv;

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
        configLineChart();

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
    public void showWaterQualityChart() {
        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<16;i++){
            lineEntry.add(new Entry(i, i));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "line");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
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


    //find the view
    private void findView(View root){
        mType_rv = root.findViewById(R.id.type_rv);
        mMapView = root.findViewById(R.id.map);
        mLineChart = root.findViewById(R.id.lineChart);

        mtemperature_tv = root.findViewById(R.id.temperature_tv);
        mPh_tv = root.findViewById(R.id.ph_tv);
        mDissolvedOxygen_tv = root.findViewById(R.id.dissolvedOxygen_tv);
        mRedox_tv = root.findViewById(R.id.redox_tv);
        mTransparency_tv = root.findViewById(R.id.transparency_tv);
        mConductivity_tv = root.findViewById(R.id.conductivity_tv);
        mTurbidity_tv = root.findViewById(R.id.turbidity_tv);
        mNh3_tv = root.findViewById(R.id.nh3_tv);
        mTab_tv = new TextView[]{mtemperature_tv, mConductivity_tv, mPh_tv,
                mDissolvedOxygen_tv, mRedox_tv,mTurbidity_tv, mTransparency_tv, mNh3_tv};
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
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setOnClickListener(this);
        }
    }

    //configure the line chart
    private void configLineChart(){
        Description description = mLineChart.getDescription();
        description.setPosition(70,20);
        description.setText("(m/s)");
        description.setTextAlign(Paint.Align.RIGHT);

        Legend legend = mLineChart.getLegend();
        legend.setEnabled(false);

        XAxis xaxis = mLineChart.getXAxis();
        xaxis.setDrawAxisLine(true);
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setLabelCount(5);

        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleYEnabled(false);
        mLineChart.setScaleXEnabled(true);
        mLineChart.setAutoScaleMinMaxEnabled(true);
    }

}
