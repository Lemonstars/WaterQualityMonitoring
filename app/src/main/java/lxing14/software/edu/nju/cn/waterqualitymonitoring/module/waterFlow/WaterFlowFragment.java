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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map.MapInfoWindowAdapter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View, View.OnClickListener{

    private WaterFlowContract.Presenter mPresenter;

    private ImageView mFlow_iv;
    private TextView mCameraHint_tv;
    private TextView mFlowNum_tv;
    private TextView mAverageFlowSpeedNum_tv;
    private TextView mDateNum_tv;
    private CameraChoiceView[] mCameraChoiceView;

    private LinearLayout mCamera_layout;
    private ImageDialog mImageDialog;
    private MapView mMapView;
    private AMap mAMap;
    private LineChart mLineChart;
    private TextView[] mTab_tv;
    private ImageView mBig_iv;
    private WebView mWebView;
    private ChartMarkerView mChartMarkerView;
    private ViewGroup cameraVideo_layout;
    private ViewGroup cameraData_layout;

    public static WaterFlowFragment generateFragment() {
        return new WaterFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_flow, container, false);

        findView(root);

        ChartUtil.configLineChart(mLineChart);
        showDescription();
        configListener();
        configChartMarkerView();
        loadWebFile();
        mPresenter.loadWaterFlowDataByDate(TimeUtil.getDateBeforeNum(7), TimeUtil.getTodayDate());
        mPresenter.loadWaterFlowVideoUrl();
        mPresenter.loadCameraInfoFromNetwork();
        mPresenter.loadAllStationInfo();

        mMapView.onCreate(savedInstanceState);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        showCurrentLocation();
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
    public void showCameraLayout(int num) {
        mCameraChoiceView = new CameraChoiceView[num];
        for(int i=0;i<num;i++){
            int j = i;
            CameraChoiceView cameraChoiceView = new CameraChoiceView(getContext(), j+1, () -> mPresenter.loadCameraInfo(j));
            mCamera_layout.addView(cameraChoiceView);
            mCameraChoiceView[i] = cameraChoiceView;
        }
    }

    @Override
    public void showStationLocation(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void showWaterFlowChartData(List<String> dateList, List<Float> dataList) {
        int len = dateList.size();
        IAxisValueFormatter iAxisValueFormatter = new IndexAxisValueFormatter(dateList);
        mLineChart.getXAxis().setValueFormatter(iAxisValueFormatter);
        mChartMarkerView.setIAxisValueFormatter(iAxisValueFormatter);
        mLineChart.setMarker(mChartMarkerView);

        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterFlow");
        lineDataSet.setLineWidth(2f);
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
    public void hideCameraLayout() {
        cameraVideo_layout.setVisibility(View.GONE);
        cameraData_layout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.oneWeek_tv:
                clickTab(CommonConstant.ONE_WEEK);
                break;
            case R.id.oneMonth_tv:
                clickTab(CommonConstant.ONE_MONTH);
                break;
            case R.id.threeMonth_tv:
                clickTab(CommonConstant.THREE_MONTH);
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

    //configure the name and the unit
    private void configChartMarkerView(){
        mAMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(), false));
        mChartMarkerView = new ChartMarkerView(getContext(),  "流量:", "m/s");
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
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(null);
        }
    }

    //show the unit of the chart
    private void showDescription(){
        Description description = mLineChart.getDescription();
        description.setText("m/s");
    }

    //find the view by the id
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mLineChart = root.findViewById(R.id.lineChart);
        TextView oneWeek_tv = root.findViewById(R.id.oneWeek_tv);
        TextView oneMonth_tv = root.findViewById(R.id.oneMonth_tv);
        TextView threeMonth_tv = root.findViewById(R.id.threeMonth_tv);
        mTab_tv = new TextView[]{oneWeek_tv, oneMonth_tv, threeMonth_tv};

        mCamera_layout = root.findViewById(R.id.camera_layout);
        mWebView = root.findViewById(R.id.webView);
        mFlow_iv = root.findViewById(R.id.flow_iv);
        mCameraHint_tv = root.findViewById(R.id.cameraHint_tv);
        mFlowNum_tv = root.findViewById(R.id.flowNum_tv);
        mAverageFlowSpeedNum_tv = root.findViewById(R.id.averageFlowSpeedNum_tv);
        mDateNum_tv = root.findViewById(R.id.dateNum_tv);
        mBig_iv = root.findViewById(R.id.big_iv);

        cameraVideo_layout = root.findViewById(R.id.cameraVideo_layout);
        cameraData_layout = root.findViewById(R.id.cameraData_layout);
    }

    //configure the listener
    private void configListener(){
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(this);
        }
        mFlow_iv.setOnClickListener(this);
        mBig_iv.setOnClickListener(this);
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
