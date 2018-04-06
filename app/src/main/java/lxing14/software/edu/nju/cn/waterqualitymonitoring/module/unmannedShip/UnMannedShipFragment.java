package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.View {

    private UnMannedShipContract.Presenter mPresenter;

    private LineChart mLineChart;
    private MapView mMapView;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);

        findView(root);
        ChartUtil.configLineChart(mLineChart);

        showWaterQualityChart();

        return root;
    }

    @Override
    public void setPresenter(UnMannedShipContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showWaterQualityChart() {
        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<20;i++){
            lineEntry.add(new Entry(i, i));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "line");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //find the view
    private void findView(android.view.View root){

        mLineChart = root.findViewById(R.id.lineChart);
        mMapView = root.findViewById(R.id.map);
    }
}
