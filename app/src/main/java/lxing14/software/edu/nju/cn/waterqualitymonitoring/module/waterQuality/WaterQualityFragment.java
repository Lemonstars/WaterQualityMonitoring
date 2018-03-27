package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class WaterQualityFragment extends Fragment implements WaterQualityContract.View{

    private WaterQualityContract.Presenter mPresenter;

    private LineChart mLineChart;
    private RecyclerView mType_rv;
    private MapView mMapView;

    public static WaterQualityFragment generateFragment(){
        return new WaterQualityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_quality, container, false);

        mType_rv = root.findViewById(R.id.type_rv);
        mMapView = root.findViewById(R.id.map);
        mLineChart = root.findViewById(R.id.lineChart);

        initRecyclerView();
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
    public void showWaterQualityChart() {
        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<16;i++){
            lineEntry.add(new Entry(i, i));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "line");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
    }

    //initialize the recyclerView
    private void initRecyclerView(){
        WaterQualityRVAdapter adapter = new WaterQualityRVAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mType_rv.setAdapter(adapter);
        mType_rv.setLayoutManager(linearLayoutManager);

        WaterQualityTypeVO vo1 = new WaterQualityTypeVO(WaterQualityTypeVO.PH, 7);
        WaterQualityTypeVO vo2 = new WaterQualityTypeVO(WaterQualityTypeVO.TEMPERATURE, 10);
        WaterQualityTypeVO vo3 = new WaterQualityTypeVO(WaterQualityTypeVO.OXYGEN, 1.33);
        List<WaterQualityTypeVO> data = new ArrayList<>();
        data.add(vo1);
        data.add(vo2);
        data.add(vo3);
        adapter.setData(data);
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
