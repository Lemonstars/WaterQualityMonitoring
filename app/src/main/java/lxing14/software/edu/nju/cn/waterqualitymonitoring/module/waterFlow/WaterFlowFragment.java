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
import android.widget.ImageView;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View, View.OnClickListener{

    private WaterFlowContract.Presenter mPresenter;

    private TextView mRealTime_tv;
    private TextView mDay_tv;
    private TextView mMonth_tv;
    private TextView[] mTab_tv;

    private ImageView mFlow_iv;
    private TextView mCameraHint_tv;
    private TextView mFlowNum_tv;
    private TextView mAverageFlowSpeedNum_tv;
    private TextView mDateNum_tv;
    private CameraChoiceView[] mCameraChoiceView;

    private ImageDialog mImageDialog;
    private MapView mMapView;
    private LineChart mLineChart;
    private ImageView mBig_iv;
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

        mPresenter.loadDefaultWaterFlowData();
        mPresenter.loadWaterFlowVideoUrl();
        mPresenter.loadCameraInfoFromNetwork();

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

    @Override
    public void showCameraInfo(int index, String date, String picUrl, double waterFlow, double waterSpeed) {
        for(int i=0;i<mCameraChoiceView.length;i++){
            if(i==index){
                mCameraChoiceView[i].setClick();
            }else {
                mCameraChoiceView[i].setUnClick();
            }
        }
        PicassoUtil.loadUrl(getContext(), picUrl, mFlow_iv);
        char targetChar = (char)('A'+index);
        mCameraHint_tv.setText("相机"+targetChar+"表面");
        mFlowNum_tv.setText(waterFlow+"m");
        mAverageFlowSpeedNum_tv.setText(waterSpeed+"m/s");
        mDateNum_tv.setText(date);
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
            case R.id.camera1:
                mPresenter.loadCameraInfo(0);
                break;
            case R.id.camera2:
                mPresenter.loadCameraInfo(1);
                break;
            case R.id.camera3:
                mPresenter.loadCameraInfo(2);
                break;
            case R.id.camera4:
                mPresenter.loadCameraInfo(3);
                break;
            case R.id.camera5:
                mPresenter.loadCameraInfo(4);
                break;
            case R.id.flow_iv:
                showSelectedPic(mFlow_iv);
                break;
            case R.id.big_iv:
                mPresenter.jumpToChartActivity();
                break;
        }
    }

    @Override
    public void onDestroy() {
        removeTabListener();
        super.onDestroy();
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //the change of the color of the tab
    private void clickTab(int index){
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(i==index ? R.color.colorPrimary:R.color.black));
        }
        mPresenter.processTab(index);
    }

    //finalize the listener
    private void removeTabListener(){
        mRealTime_tv.setOnClickListener(null);
        mDay_tv.setOnClickListener(null);
        mMonth_tv.setOnClickListener(null);
    }

    //show the unit of the chart
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
        mLineChart = root.findViewById(R.id.lineChart);
        mRealTime_tv = root.findViewById(R.id.realTime_tv);
        mDay_tv = root.findViewById(R.id.day_tv);
        mMonth_tv = root.findViewById(R.id.month_tv);
        mWebView = root.findViewById(R.id.webView);
        mTab_tv = new TextView[]{mRealTime_tv, mDay_tv, mMonth_tv};

        mFlow_iv = root.findViewById(R.id.flow_iv);
        mCameraHint_tv = root.findViewById(R.id.cameraHint_tv);
        mFlowNum_tv = root.findViewById(R.id.flowNum_tv);
        mAverageFlowSpeedNum_tv = root.findViewById(R.id.averageFlowSpeedNum_tv);
        mDateNum_tv = root.findViewById(R.id.dateNum_tv);
        mBig_iv = root.findViewById(R.id.big_iv);

        CameraChoiceView camera1 = root.findViewById(R.id.camera1);
        CameraChoiceView camera2 = root.findViewById(R.id.camera2);
        CameraChoiceView camera3 = root.findViewById(R.id.camera3);
        CameraChoiceView camera4 = root.findViewById(R.id.camera4);
        CameraChoiceView camera5 = root.findViewById(R.id.camera5);
        mCameraChoiceView = new CameraChoiceView[]{camera1, camera2, camera3, camera4, camera5};
    }

    //configure the listener
    private void configListener(){
        mRealTime_tv.setOnClickListener(this);
        mDay_tv.setOnClickListener(this);
        mMonth_tv.setOnClickListener(this);
        mFlow_iv.setOnClickListener(this);
        mBig_iv.setOnClickListener(this);
        for(CameraChoiceView cameraChoiceView: mCameraChoiceView){
            cameraChoiceView.setOnClickListener(this);
        }
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        AMap aMap = mMapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }

    //show the selected picture
    private void showSelectedPic(ImageView imageView){
        if(mImageDialog == null){
            mImageDialog = new ImageDialog(getContext(), imageView.getDrawable());
        }else {
            mImageDialog.setImage(imageView.getDrawable());
        }
        mImageDialog.show();
    }

    private void loadWebFile(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.loadUrl("javascript:getRealTimeStream('ws://47.92.84.138:8599/123')");
    }

}
