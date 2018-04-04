package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View, View.OnClickListener{

    private WaterFlowContract.Presenter mPresenter;


    private TextView mRealTime_tv;
    private TextView mDay_tv;
    private TextView mMonth_tv;
    private TextView[] mTab_tv;

    private MapView mMapView;
    private LinearLayout mCamera_layout;
    private LineChart mLineChart;
    private WebView mWebView;

    public static WaterFlowFragment generateFragment() {
        return new WaterFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_flow, container, false);

        findView(root);

        ChartUtil.configLineChart(mLineChart);
        showInitTabSelected();
        showDescription();
        configListener();
        loadWebFile();

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
    public void showWaterFlowChartData(List<String> dateList, List<Float> dataList) {
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
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
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
                clickTab(CommonConstant.REAL_TIME);
                break;
            case R.id.day_tv:
                clickTab(CommonConstant.DAY);
                break;
            case R.id.month_tv:
                clickTab(CommonConstant.MONTH);
                break;
        }
    }

    @Override
    public void onDestroy() {
        removeTabListener();
        super.onDestroy();
    }

    private void showDescription(){
        Description description = mLineChart.getDescription();
        description.setText("m/s");
    }

    //show the selected tab at first
    private void showInitTabSelected(){
        mRealTime_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    //find the view by the id
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mCamera_layout = root.findViewById(R.id.camera_layout);
        mLineChart = root.findViewById(R.id.lineChart);
        mRealTime_tv = root.findViewById(R.id.realTime_tv);
        mDay_tv = root.findViewById(R.id.day_tv);
        mMonth_tv = root.findViewById(R.id.month_tv);
        mWebView = root.findViewById(R.id.webView);
        mTab_tv = new TextView[]{mRealTime_tv, mDay_tv, mMonth_tv};
    }

    //the change of the color of the tab
    private void clickTab(int index){
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(i==index ? R.color.colorPrimary:R.color.black));
        }
        mPresenter.processTab(index);
    }

    //configure the listener
    private void configListener(){
        mRealTime_tv.setOnClickListener(this);
        mDay_tv.setOnClickListener(this);
        mMonth_tv.setOnClickListener(this);
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        AMap aMap = mMapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }

    private void loadWebFile(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.loadUrl("javascript:getRealTimeStream('ws://47.92.84.138:8599/123')");
    }

}
