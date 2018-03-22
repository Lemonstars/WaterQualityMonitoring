package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amap.api.maps.MapView;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View {

    private WaterFlowContract.Presenter mPresenter;

    private MapView mMapView;
    private CandleStickChart mChart;
    private LinearLayout mCamera_layout;

    public static WaterFlowFragment generateFragment() {
        return new WaterFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_flow, container, false);

        mMapView = root.findViewById(R.id.map);
        mChart = root.findViewById(R.id.chart);
        mCamera_layout = root.findViewById(R.id.camera_layout);

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
    public void setPresenter(WaterFlowContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showCameraChoiceView(int num) {
        CameraChoiceView cameraChoiceView;
        for(int i=0;i<num;i++){
            char ch = (char)('A' + i);
            cameraChoiceView = new CameraChoiceView(getContext(), ch);
            mCamera_layout.addView(cameraChoiceView);
        }
    }

    @Override
    public void showWaterLevelChart() {
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        mChart.setBorderWidth(1f);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(false);
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(false);
        mChart.animateX(2500);

        Legend legend  = mChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(10f, 5f, 0f);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisLeft.setLabelCount(4, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);

        List<CandleEntry> candleEntryList = new ArrayList<>();
        for(int i=0;i<500;i++){
            candleEntryList.add(new CandleEntry(i, 2*i, i, 2*i, i));
        }
        CandleDataSet candleDataSet = new CandleDataSet(candleEntryList, "data");
        candleDataSet.setShadowWidth(0.7f);
        candleDataSet.setDecreasingColor(R.color.temperature_color);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(R.color.temperature_color);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setHighlightLineWidth(1f);
        candleDataSet.setDrawValues(false);

        CandleData candleData = new CandleData(candleDataSet);

        mChart.setData(candleData);

    }
}
