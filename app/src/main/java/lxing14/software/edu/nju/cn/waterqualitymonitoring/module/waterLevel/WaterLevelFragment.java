package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View, View.OnClickListener{

    private WaterLevelContract.Presenter mPresenter;

    private LineChart mLineChart;
    private MapView mMapView;
    private AMap mAMap;
    private ImageView mCurrentWaterLevelImg_iv;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private TextView mCurrentWaterLevelNum_tv;
    private TextView mHistoricalWaterLevelNum_tv;
    private TextView mPhotoByDate_tv;
    private ImageDialog mImageDialog;
    private ImageView mBig_iv;
    private TextView[] mTab_tv;
    private ChartMarkerView mChartMarkerView;

    public static WaterLevelFragment generateFragment(){
        WaterLevelFragment waterLevelFragment = new WaterLevelFragment();
        Bundle bundle = new Bundle();
        waterLevelFragment.setArguments(bundle);

        return waterLevelFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_level, container, false);

        findView(root);
        configListener();
        configChartMarkerView();
        ChartUtil.configLineChart(mLineChart);
        showChartUnit();

        String startTime = TimeUtil.getDateBeforeNum(7);
        String endTime = TimeUtil.getTodayDate();
        mPresenter.loadWaterLevelDataByDate(startTime, endTime);
        mPresenter.loadCurrentWaterLevelInfo();
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
    public void setPresenter(WaterLevelContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showStationLocation(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void showWaterLevelChartData(List<String> waterLevelDate, List<Float> waterLevelData) {
        int len = waterLevelDate.size();
        IAxisValueFormatter iAxisValueFormatter = new IndexAxisValueFormatter(waterLevelDate);
        mLineChart.getXAxis().setValueFormatter(iAxisValueFormatter);
        mChartMarkerView.setIAxisValueFormatter(iAxisValueFormatter);
        mLineChart.setMarker(mChartMarkerView);

        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, waterLevelData.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterLevel");
        lineDataSet.setLineWidth(2f);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public void showCurrentWaterLevelDetailInfo(String currentWaterLevel, String historicalWaterLevel,
                                                String[] picUrl, String[] date) {
        mCurrentWaterLevelNum_tv.setText(currentWaterLevel);
        mHistoricalWaterLevelNum_tv.setText(historicalWaterLevel);
        mPhotoByDate_tv.setText(date[0]);

        PicassoUtil.loadUrl(getContext(), picUrl[0], mCurrentWaterLevelImg_iv);
        PicassoUtil.loadUrl(getContext(), picUrl[1], mImage1);
        PicassoUtil.loadUrl(getContext(), picUrl[2], mImage2);
        PicassoUtil.loadUrl(getContext(), picUrl[3], mImage3);
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
            case R.id.currentWaterLevelImg_iv:
                showSelectedPic(mCurrentWaterLevelImg_iv);
                break;
            case R.id.image1:
                showSelectedPic(mImage1);
                break;
            case R.id.image2:
                showSelectedPic(mImage2);
                break;
            case R.id.image3:
                showSelectedPic(mImage3);
                break;
            case R.id.big_iv:
                mPresenter.jumpToChartActivity();
                break;
        }
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //configure the markerView
    private void configChartMarkerView(){
        mAMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(), false));
        mChartMarkerView = new ChartMarkerView(getContext(),  "水位:", "m");
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
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

    //show the unit of the chart
    private void showChartUnit(){
        Description description = mLineChart.getDescription();
        description.setText("m");
    }

    //the change of the color of the tab
    private void clickTab(int index){
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(i==index ? R.color.colorPrimary:R.color.black));
        }
        mPresenter.processTab(index);
    }

    //find the view by the id
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();

        mCurrentWaterLevelImg_iv = root.findViewById(R.id.currentWaterLevelImg_iv);
        mImage1 = root.findViewById(R.id.image1);
        mImage2 = root.findViewById(R.id.image2);
        mImage3 = root.findViewById(R.id.image3);
        mCurrentWaterLevelNum_tv = root.findViewById(R.id.currentWaterLevelNum_tv);
        mHistoricalWaterLevelNum_tv = root.findViewById(R.id.historicalWaterLevelNum_tv);
        mPhotoByDate_tv = root.findViewById(R.id.photoByDate_tv);
        mLineChart = root.findViewById(R.id.lineChart);
        mBig_iv = root.findViewById(R.id.big_iv);

        TextView oneWeek_tv = root.findViewById(R.id.oneWeek_tv);
        TextView oneMonth_tv = root.findViewById(R.id.oneMonth_tv);
        TextView threeMonth_tv = root.findViewById(R.id.threeMonth_tv);
        mTab_tv = new TextView[]{oneWeek_tv, oneMonth_tv, threeMonth_tv};
    }

    //configure the listener
    private void configListener(){
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(this);
        }
        mCurrentWaterLevelImg_iv.setOnClickListener(this);
        mBig_iv.setOnClickListener(this);
        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
    }
}
