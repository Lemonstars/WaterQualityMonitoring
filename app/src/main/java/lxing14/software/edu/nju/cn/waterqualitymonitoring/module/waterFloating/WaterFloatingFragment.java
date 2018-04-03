package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;


public class WaterFloatingFragment extends Fragment implements WaterFloatingContract.IView{

    private WaterFloatingContract.IPresenter mPresenter;
    private BarChart mBarChart;
    private WebView mWebView;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;


    public static WaterFloatingFragment generateFragment(){
        return new WaterFloatingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_water_floationg, container, false);

        mBarChart = root.findViewById(R.id.barChart);
        mWebView = root.findViewById(R.id.webView);
        mImage1 = root.findViewById(R.id.image1);
        mImage2 = root.findViewById(R.id.image2);
        mImage3 = root.findViewById(R.id.image3);

        configBarChart();

        loadWebFile();

        return root;
    }

    @Override
    public void setPresenter(WaterFloatingContract.IPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showBarChart() {
        List<BarEntry> barEntryList = new ArrayList<>();
        for(int i=0;i<20;i++){
            barEntryList.add(new BarEntry(i, i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntryList, "bar");
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    //configure the bar chart
    private void configBarChart(){
        Legend legend = mBarChart.getLegend();
        legend.setEnabled(false);

        XAxis xaxis = mBarChart.getXAxis();
        xaxis.setDrawAxisLine(true);
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setLabelCount(5);

        YAxis yAxisLeft = mBarChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mBarChart.getAxisRight();
        yAxisRight.setEnabled(false);

        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleYEnabled(false);
        mBarChart.setScaleXEnabled(true);
        mBarChart.setAutoScaleMinMaxEnabled(true);
    }

    private void loadWebFile(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.loadUrl("javascript:getRealTimeStream('ws://47.92.84.138:8599/123')");
    }

}
