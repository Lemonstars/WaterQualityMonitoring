package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View{

    private WaterLevelContract.Presenter mPresenter;

    private MapView mMapView;
    private LineChartView mChart;

    public static WaterLevelFragment generateFragment(){
        return new WaterLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_level, container, false);

        mMapView = (MapView) root.findViewById(R.id.map);
        mChart = (LineChartView) root.findViewById(R.id.chart);

        showChart();

        mMapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void setPresenter(WaterLevelContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void showChart(){

        String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};
        int[] score= {50,42,90,33,10,74,22,18,79,20};
        List<PointValue> mPointValues = new ArrayList<PointValue>();
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }

        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }

        LineChartData data = new LineChartData();

        Line line = new Line(mPointValues)
                .setColor(ContextCompat.getColor(getContext() ,R.color.lightBlue))
                .setPointRadius(2)
                .setStrokeWidth(1)
                .setCubic(true)
                .setHasLabels(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        //x轴
        Axis axisX = new Axis();
        axisX.setTextSize(8);
        axisX.setValues(mAxisXValues);
        axisX.setHasLines(true);

        //Y轴
        Axis axisY = new Axis();
        axisY.setName(getString(R.string.chartOfCurrentWaterLevel));
        axisY.setTextSize(8);

        data.setLines(lines);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        //设置行为属性，支持缩放、滑动以及平移
        mChart.setInteractive(true);
        mChart.setZoomType(ZoomType.HORIZONTAL);
        mChart.setMaxZoom((float) 2);//最大方法比例
        mChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mChart.setLineChartData(data);

    }

}
