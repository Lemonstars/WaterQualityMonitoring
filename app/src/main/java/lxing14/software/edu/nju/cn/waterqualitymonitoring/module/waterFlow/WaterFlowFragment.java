package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View, View.OnClickListener{

    private WaterFlowContract.Presenter mPresenter;

    private MapView mMapView;
    private LinearLayout mCamera_layout;
    private CandleStickChart mCandleStickChart;
    private LineChart mLineChart;
    private TextView mRealTime_tv;
    private TextView mDay_tv;
    private TextView mMonth_tv;
    private WebView mWebView;

    public static WaterFlowFragment generateFragment() {
        return new WaterFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_flow, container, false);

        mMapView = root.findViewById(R.id.map);
        mCamera_layout = root.findViewById(R.id.camera_layout);
        mCandleStickChart = root.findViewById(R.id.candleStickChart);
        mLineChart = root.findViewById(R.id.lineChart);
        mRealTime_tv = root.findViewById(R.id.realTime_tv);
        mDay_tv = root.findViewById(R.id.day_tv);
        mMonth_tv = root.findViewById(R.id.month_tv);
        mWebView = root.findViewById(R.id.webView);

        configLineChart();
        configCandleStickChart();
        initTabListener();
        loadWebFile();

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
    public void showRealTimeChart(List<String> dateList, List<Float> dataList) {
        mCandleStickChart.setVisibility(View.GONE);
        mLineChart.setVisibility(View.VISIBLE);

        mRealTime_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        mDay_tv.setTextColor(getResources().getColor(R.color.black));
        mMonth_tv.setTextColor(getResources().getColor(R.color.black));

        int len = dateList.size();
        mLineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dateList));

        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterFlowRealTime");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    @Override
    public void showDayChart() {
        mLineChart.setVisibility(View.GONE);
        mCandleStickChart.setVisibility(View.VISIBLE);

        mDay_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        mRealTime_tv.setTextColor(getResources().getColor(R.color.black));
        mMonth_tv.setTextColor(getResources().getColor(R.color.black));


        List<CandleEntry> candleEntryList = new ArrayList<>();
        for(int i=1;i<50;i++){
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
        mCandleStickChart.setData(candleData);
    }


    @Override
    public void showMonthChart() {
        mLineChart.setVisibility(View.GONE);
        mCandleStickChart.setVisibility(View.VISIBLE);

        mMonth_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        mDay_tv.setTextColor(getResources().getColor(R.color.black));
        mRealTime_tv.setTextColor(getResources().getColor(R.color.black));
    }

    //configure the candle stick chart
    private void configCandleStickChart(){
        Description description = mCandleStickChart.getDescription();
        description.setPosition(70,20);
        description.setText("(m/s)");
        description.setTextAlign(Paint.Align.RIGHT);

        Legend legend  = mCandleStickChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = mCandleStickChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelCount(2);
        xAxis.setAxisMaximum(15);

        YAxis yAxisLeft = mCandleStickChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mCandleStickChart.getAxisRight();
        yAxisRight.setEnabled(false);

        mCandleStickChart.setEnabled(true);
        mCandleStickChart.setTouchEnabled(true);
        mCandleStickChart.setDragEnabled(true);
        mCandleStickChart.setScaleYEnabled(false);
        mCandleStickChart.setScaleXEnabled(true);
        mCandleStickChart.setAutoScaleMinMaxEnabled(true);
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
        xaxis.setLabelCount(2);
        xaxis.setAxisMaximum(15);

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

    //initialize the listener
    private void initTabListener(){
        mRealTime_tv.setOnClickListener(this);
        mDay_tv.setOnClickListener(this);
        mMonth_tv.setOnClickListener(this);
    }

    //finalize the listener
    private void removeTabListener(){
        mRealTime_tv.setOnClickListener(null);
        mDay_tv.setOnClickListener(null);
        mMonth_tv.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.realTime_tv:
                mPresenter.loadChartData();
                break;
            case R.id.day_tv:
                showDayChart();
                break;
            case R.id.month_tv:
                showMonthChart();
                break;
        }
    }

    @Override
    public void onDestroy() {
        removeTabListener();
        super.onDestroy();
    }

    private void loadWebFile(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.loadUrl("javascript:getRealTimeStream('ws://47.92.84.138:8599/123')");
    }

}
