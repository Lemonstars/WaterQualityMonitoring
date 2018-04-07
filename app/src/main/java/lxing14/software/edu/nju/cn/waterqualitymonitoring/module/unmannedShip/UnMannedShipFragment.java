package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.MapView;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.View, View.OnClickListener {

    private UnMannedShipContract.Presenter mPresenter;

    private MapView mMapView;
    private LineChart mLineChart;
    private ImageView mBig_iv;
    private TextView[] mTab_tv;
    private ChartMarkerView mChartMarkerView;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);

        findView(root);
        configChartMarkerView();
        configListener();
        ChartUtil.configLineChart(mLineChart);

        mPresenter.loadChartDataByType(WaterQualityData.TEMPERATURE);

        return root;
    }

    @Override
    public void setPresenter(UnMannedShipContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showWaterQualityChart(List<String> dateList, List<Float> dataList) {
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
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public void showTabSelected(int index) {
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(index==i?R.color.colorPrimary:R.color.black));
        }
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
    public Context getContextView() {
        return getContext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.temperature_tv:
                mPresenter.loadChartDataByType(WaterQualityData.TEMPERATURE);
                break;
            case R.id.conductivity_tv:
                mPresenter.loadChartDataByType(WaterQualityData.ELECTRIC);
                break;
            case R.id.ph_tv:
                mPresenter.loadChartDataByType(WaterQualityData.PH);
                break;
            case R.id.dissolvedOxygen_tv:
                mPresenter.loadChartDataByType(WaterQualityData.OXYGEN);
                break;
            case R.id.redox_tv:
                mPresenter.loadChartDataByType(WaterQualityData.OXIDATION);
                break;
            case R.id.turbidity_tv:
                mPresenter.loadChartDataByType(WaterQualityData.DIRTY);
                break;
            case R.id.transparency_tv:
                mPresenter.loadChartDataByType(WaterQualityData.TRANSPANENCY);
                break;
            case R.id.nh3_tv:
                mPresenter.loadChartDataByType(WaterQualityData.AMMONIA);
                break;
            case R.id.big_iv:
                mPresenter.jumpToChartActivity();
                break;
        }
    }

    //configure the name and the unit
    private void configChartMarkerView(){
        mChartMarkerView = new ChartMarkerView(getContext(),  "温度:", "°C");
    }

    //find the view
    private void findView(android.view.View root){

        mMapView = root.findViewById(R.id.map);
        mLineChart = root.findViewById(R.id.lineChart);
        mBig_iv = root.findViewById(R.id.big_iv);

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

    //configure the listener
    private void configListener(){
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(this);
        }
        mBig_iv.setOnClickListener(this);
    }
}
