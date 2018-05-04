package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;


public class WaterFloatingFragment extends Fragment implements WaterFloatingContract.View, android.view.View.OnClickListener{

    private WaterFloatingContract.Presenter mPresenter;

    private LineChart mLineChart;
    private MapView mMapView;
    private AMap mAMap;
    private TextView[] mTab_tv;
    private ImageView mBig_iv;
    private ImageView mImage0;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageDialog mImageDialog;
    private ChartMarkerView mChartMarkerView;

    public static WaterFloatingFragment generateFragment(){
        return new WaterFloatingFragment();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root =  inflater.inflate(R.layout.fragment_water_floationg, container, false);

        findView(root);
        ChartUtil.configLineChart(mLineChart);
        configUnit();
        configListener();
        configChartMarkerView();

        mPresenter.loadWaterFloatingChartByDate(TimeUtil.getDateBeforeNum(7), TimeUtil.getTodayDate());
        mPresenter.loadWaterFloatingPicURl();
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
    public void setPresenter(WaterFloatingContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showStationLocation(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void showFloatingChart(List<String> dateList, List<Integer> dataList) {
        int len = dateList.size();
        IAxisValueFormatter iAxisValueFormatter = new IndexAxisValueFormatter(dateList);
        mLineChart.getXAxis().setValueFormatter(iAxisValueFormatter);
        mChartMarkerView.setIAxisValueFormatter(iAxisValueFormatter);
        mLineChart.setMarker(mChartMarkerView);


        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "floating");
        lineDataSet.setLineWidth(2f);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public void showFloatingPic(String url0, String url1, String url2, String url3) {
        Context context = getContext();
        PicassoUtil.loadUrl(context, url0, mImage0);
        PicassoUtil.loadUrl(context, url1, mImage1);
        PicassoUtil.loadUrl(context, url2, mImage2);
        PicassoUtil.loadUrl(context, url3, mImage3);
    }

    @Override
    public void onClick(android.view.View view) {
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
            case R.id.image0:
                showSelectedPic(mImage0);
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

    //configure the name and the unit
    private void configChartMarkerView(){
        mAMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(), false));
        mChartMarkerView = new ChartMarkerView(getContext(),  "漂浮物:", "个");
    }

    //the change of the color of the tab
    private void clickTab(int index){
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(i==index ? R.color.colorPrimary:R.color.black));
        }
        mPresenter.processTab(index);
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

    //configure the listener
    private void configListener(){
        for(TextView textView: mTab_tv){
            textView.setOnClickListener(this);
        }
        mImage0.setOnClickListener(this);
        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
        mBig_iv.setOnClickListener(this);
    }

    //find the view by the id
    private void findView(android.view.View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        TextView oneWeek_tv = root.findViewById(R.id.oneWeek_tv);
        TextView oneMonth_tv = root.findViewById(R.id.oneMonth_tv);
        TextView threeMonth_tv = root.findViewById(R.id.threeMonth_tv);
        mTab_tv = new TextView[]{oneWeek_tv, oneMonth_tv, threeMonth_tv};
        mLineChart = root.findViewById(R.id.lineChart);
        mBig_iv = root.findViewById(R.id.big_iv);
        mImage0 = root.findViewById(R.id.image0);
        mImage1 = root.findViewById(R.id.image1);
        mImage2 = root.findViewById(R.id.image2);
        mImage3 = root.findViewById(R.id.image3);
    }

    private void configUnit(){
        Description description = mLineChart.getDescription();
        description.setText("个");
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }


}
