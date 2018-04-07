package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ChartMarkerView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/2
 * @time : 上午9:33
 */

public class ChartUtil {

    public static void configLineChart(LineChart lineChart){
        lineChart.setNoDataText("正在加载数据");
        lineChart.setNoDataTextColor(R.color.black);

        Description description = lineChart.getDescription();
        description.setPosition(70,20);
        description.setTextAlign(Paint.Align.RIGHT);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        XAxis xaxis = lineChart.getXAxis();
        xaxis.setDrawAxisLine(true);
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setLabelCount(2);
        xaxis.setGranularityEnabled(true);
        xaxis.setGranularity(10f);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(15f);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleYEnabled(false);
        lineChart.setScaleXEnabled(true);
        lineChart.setAutoScaleMinMaxEnabled(true);
    }

}
