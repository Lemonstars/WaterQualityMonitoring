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
        List<CandleEntry> candleEntryList = new ArrayList<>();
        for(int i=0;i<50;i++){
            candleEntryList.add(new CandleEntry(i, 2*i, i, 2*i, i));
        }
        CandleDataSet candleDataSet = new CandleDataSet(candleEntryList, "data");
        candleDataSet.setHighlightEnabled(false);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setDecreasingColor(Color.GREEN);
        candleDataSet.setIncreasingColor(Color.RED);
        candleDataSet.setShadowColorSameAsCandle(true);
        candleDataSet.setShowCandleBar(true);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.STROKE);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE);

        CandleData candleData = new CandleData(candleDataSet);
        mChart.setData(candleData);
    }
}
