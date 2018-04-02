package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View, View.OnClickListener{

    private WaterLevelContract.Presenter mPresenter;

    private LineChart mLineChart;
    private MapView mMapView;
    private AMap mAMap;
    private ImageView mCurrentWaterLevelImg_iv;
    private TextView mCurrentWaterLevelNum_tv;
    private TextView mHistoricalWaterLevelNum_tv;
    private TextView mPhotoByDate_tv;
    private ImageDialog mImageDialog;

    private TextView mRealTime_tv;
    private TextView mDay_tv;
    private TextView mMonth_tv;
    private TextView[] mTab_tv;

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
        ChartUtil.configLineChart(mLineChart);

        mPresenter.loadLocationInfo();

        mMapView.onCreate(savedInstanceState);
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
        mRealTime_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        mDay_tv.setTextColor(getResources().getColor(R.color.black));
        mMonth_tv.setTextColor(getResources().getColor(R.color.black));

        int len = waterLevelDate.size();
        mLineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(waterLevelDate));

        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, waterLevelData.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "waterLevel");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    @Override
    public void showCurrentWaterLevelDetailInfo(String picUrl, String currentWaterLevel,
                                                String historicalWaterLevel, String photoByDate) {
        PicassoUtil.loadUrl(getContext(), picUrl, mCurrentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv.setText(currentWaterLevel);
        mHistoricalWaterLevelNum_tv.setText(historicalWaterLevel);
        mPhotoByDate_tv.setText(photoByDate);
    }

    @Override
    public void showCurrentLocation(double latitude, double longitude) {
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.realTime_tv:
                clickTab(WaterLevelPresenter.REAL_TIME);
                break;
            case R.id.day_tv:
                clickTab(WaterLevelPresenter.DAY);
                break;
            case R.id.month_tv:
                clickTab(WaterLevelPresenter.MONTH);
                break;
            case R.id.currentWaterLevelImg_iv:
                if(mImageDialog == null){
                    mImageDialog = new ImageDialog(getContext(), mCurrentWaterLevelImg_iv.getDrawable());
                }else {
                    mImageDialog.setImage(mCurrentWaterLevelImg_iv.getDrawable());
                }
                mImageDialog.show();
                break;
        }
    }

    //the change of the color of the tab
    private void clickTab(int index){
        for(int i=0;i<mTab_tv.length;i++){
            mTab_tv[i].setTextColor(getResources().getColor(i==index ? R.color.colorPrimary:R.color.black));
        }
    }


    //find the view by the id
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();

        mCurrentWaterLevelImg_iv = root.findViewById(R.id.currentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv = root.findViewById(R.id.currentWaterLevelNum_tv);
        mHistoricalWaterLevelNum_tv = root.findViewById(R.id.historicalWaterLevelNum_tv);
        mPhotoByDate_tv = root.findViewById(R.id.photoByDate_tv);
        mLineChart = root.findViewById(R.id.lineChart);

        mRealTime_tv = root.findViewById(R.id.realTime_tv);
        mDay_tv = root.findViewById(R.id.day_tv);
        mMonth_tv = root.findViewById(R.id.month_tv);
        mTab_tv = new TextView[]{mRealTime_tv, mDay_tv, mMonth_tv};
    }

    //configure the listener
    private void configListener(){
        mRealTime_tv.setOnClickListener(this);
        mDay_tv.setOnClickListener(this);
        mMonth_tv.setOnClickListener(this);
        mCurrentWaterLevelImg_iv.setOnClickListener(this);
    }

}
