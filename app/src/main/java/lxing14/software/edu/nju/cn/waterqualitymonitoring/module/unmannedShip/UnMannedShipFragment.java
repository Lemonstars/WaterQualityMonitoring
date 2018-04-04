package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.IView{

    private UnMannedShipContract.IPresenter mPresenter;

    private LineChart mLineChart;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);

        mLineChart = root.findViewById(R.id.lineChart);

        ChartUtil.configLineChart(mLineChart);

        return root;
    }

    @Override
    public void setPresenter(UnMannedShipContract.IPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
}
