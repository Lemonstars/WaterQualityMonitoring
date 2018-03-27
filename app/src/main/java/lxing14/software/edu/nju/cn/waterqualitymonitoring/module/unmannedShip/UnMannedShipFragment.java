package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

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

        configLineChart();

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
    }

    //configure the line chart
    private void configLineChart(){
        Description description = mLineChart.getDescription();
        description.setPosition(70,20);
        description.setText("(m/s)");
        description.setTextAlign(Paint.Align.RIGHT);

        Legend legend = mLineChart.getLegend();
        legend.setEnabled(false);

        XAxis xaxis = mLineChart.getXAxis();
        xaxis.setDrawAxisLine(true);
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setLabelCount(5);

        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleYEnabled(false);
        mLineChart.setScaleXEnabled(true);
        mLineChart.setAutoScaleMinMaxEnabled(true);
    }

}
