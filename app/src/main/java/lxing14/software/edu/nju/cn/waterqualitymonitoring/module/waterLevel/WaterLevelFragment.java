package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart.ChartActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View{

    private WaterLevelContract.Presenter mPresenter;

    private LineChart mLineChart;
    private MapView mMapView;
    private ImageView mCurrentWaterLevelImg_iv;
    private TextView mCurrentWaterLevelNum_tv;
    private TextView mHistoricalWaterLevelNum_tv;
    private TextView mPhotoByDate_tv;

    public static WaterLevelFragment generateFragment(){
        return new WaterLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_level, container, false);

        mMapView = root.findViewById(R.id.map);

        mCurrentWaterLevelImg_iv = root.findViewById(R.id.currentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv = root.findViewById(R.id.currentWaterLevelNum_tv);
        mHistoricalWaterLevelNum_tv = root.findViewById(R.id.historicalWaterLevelNum_tv);
        mPhotoByDate_tv = root.findViewById(R.id.photoByDate_tv);
        mLineChart = root.findViewById(R.id.lineChart);

        mMapView.onCreate(savedInstanceState);

        configLineChart();
        configListener();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

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
    public void setPresenter(WaterLevelContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showWaterLevelInfo(List<String> waterLevelDate, List<Float> waterLevelData) {
        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<15;i++){
            lineEntry.add(new Entry(i, i));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "line");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
    }

    @Override
    public void showCurrentWaterLevelDetailInfo(String picUrl, String currentWaterLevel,
                                                String historicalWaterLevel, String photoByDate) {
        PicassoUtil.loadUrl(getContext(), picUrl, mCurrentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv.setText(currentWaterLevel);
        mHistoricalWaterLevelNum_tv.setText(historicalWaterLevel);
        mPhotoByDate_tv.setText(photoByDate);
    }

    //configure the listener
    private void configListener(){
        mLineChart.setOnLongClickListener( e -> {startActivity(new Intent(getContext(), ChartActivity.class));return true;});
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
