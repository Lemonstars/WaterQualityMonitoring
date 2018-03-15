package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View{

    private WaterLevelContract.Presenter mPresenter;

    private MapView mMapView;
    private LineChartView mChart;
    private ImageView mCurrentWaterLevelImg_iv;
    private TextView mCurrentWaterLevelNum_tv;
    private TextView mHistoricalWaterLevelNum_tv;
    private TextView mPhotoByDate_tv;

    public static WaterLevelFragment generateFragment(){
        return new WaterLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_level, container, false);

        mMapView = root.findViewById(R.id.map);
        mChart = root.findViewById(R.id.chart);

        mCurrentWaterLevelImg_iv = root.findViewById(R.id.currentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv = root.findViewById(R.id.currentWaterLevelNum_tv);
        mHistoricalWaterLevelNum_tv = root.findViewById(R.id.historicalWaterLevelNum_tv);
        mPhotoByDate_tv = root.findViewById(R.id.photoByDate_tv);

        mMapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.start();
    }

    @Override
    public void setPresenter(WaterLevelContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showWaterLevelInfo(List<String> waterLevelDate, List<Float> waterLevelData) {
        List<PointValue> mPointValues = new ArrayList<PointValue>();
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

        for (int i = 0; i < waterLevelDate.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(waterLevelDate.get(i)));
        }

        for (int i = 0; i < waterLevelData.size(); i++) {
            PointValue pointValue = new PointValue(i, waterLevelData.get(i));
            pointValue.setLabel(String.valueOf(waterLevelData.get(i)));
            mPointValues.add(pointValue);
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

    @Override
    public void showCurrentWaterLevelDetailInfo(String picUrl, String currentWaterLevel,
                                                String historicalWaterLevel, String photoByDate) {
        PicassoUtil.loadUrl(getContext(), picUrl, mCurrentWaterLevelImg_iv);
        mCurrentWaterLevelNum_tv.setText(currentWaterLevel);
        mHistoricalWaterLevelNum_tv.setText(historicalWaterLevel);
        mPhotoByDate_tv.setText(photoByDate);
    }
}
