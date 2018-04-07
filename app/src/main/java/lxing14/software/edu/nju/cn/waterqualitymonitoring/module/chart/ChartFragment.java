package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ChartUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;

public class ChartFragment extends Fragment implements ChartContract.View{

    private ChartContract.Presenter mPresenter;

    private LineChart mLineChart;
    private TextView mName_tv;
    private ChartMarkerView mChartMarkerView;

    public static ChartFragment generateFragment(){
        return new ChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        findView(root);
        ChartUtil.configLineChart(mLineChart);
        configChartMarkerView();
        mPresenter.loadChartData();

        return root;
    }

    @Override
    public void setPresenter(ChartContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showChart(List<String> dateList, List<Float> dataList) {
        int len = dateList.size();
        IAxisValueFormatter iAxisValueFormatter = new IndexAxisValueFormatter(dateList);
        mLineChart.getXAxis().setValueFormatter(iAxisValueFormatter);
        mChartMarkerView.setIAxisValueFormatter(iAxisValueFormatter);
        mLineChart.setMarker(mChartMarkerView);

        List<Entry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new Entry(i, dataList.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntry, "data");
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(15f);
        mLineChart.moveViewToX(0);
        mLineChart.invalidate();
    }

    @Override
    public void showChartName(String name) {
        mName_tv.setText(name);
    }

    @Override
    public void showChartUnit(String unit) {
        Description description = mLineChart.getDescription();
        description.setText(unit);
    }

    @Override
    public void configChartMarkerView(String entry, String unit) {
        mChartMarkerView.setEntry(entry);
        mChartMarkerView.setUnit(unit);
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //configure the name and the unit
    private void configChartMarkerView(){
        mChartMarkerView = new ChartMarkerView(getContext());
    }

    //find the view
    private void findView(View root){
        mLineChart = root.findViewById(R.id.lineChart);
        mName_tv = root.findViewById(R.id.name_tv);
    }

}
