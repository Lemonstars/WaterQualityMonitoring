package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.ImageDialog;


public class WaterFloatingFragment extends Fragment implements WaterFloatingContract.IView, View.OnClickListener{

    private WaterFloatingContract.IPresenter mPresenter;
    private BarChart mBarChart;
    private WebView mWebView;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageDialog mImageDialog;


    public static WaterFloatingFragment generateFragment(){
        return new WaterFloatingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_water_floationg, container, false);

        findView(root);
        configBarChart();
        loadWebFile();
        configListener();

        mPresenter.loadWaterFloatingChartByDate(TimeUtil.getDateBeforeNum(7), TimeUtil.getTodayDate());
        mPresenter.loadWaterFloatingPicURl();

        return root;
    }

    @Override
    public void setPresenter(WaterFloatingContract.IPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showBarChart(List<String> dateList, List<Integer> dataList) {
        int len = dateList.size();
        mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dateList));

        List<BarEntry> lineEntry = new ArrayList<>();
        for(int i=0;i<len;i++){
            lineEntry.add(new BarEntry(i, dataList.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(lineEntry, "floating");
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);
        mBarChart.notifyDataSetChanged();
        mBarChart.setVisibleXRangeMaximum(15f);
        mBarChart.moveViewToX(0);
        mBarChart.invalidate();
    }

    @Override
    public void showFloatingPic(String url1, String url2, String url3) {
        PicassoUtil.loadUrl(getContext(), url1, mImage1);
        PicassoUtil.loadUrl(getContext(), url2, mImage2);
        PicassoUtil.loadUrl(getContext(), url3, mImage3);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image1:
                showSelectedPic(mImage1);
                break;
            case R.id.image2:
                showSelectedPic(mImage2);
                break;
            case R.id.image3:
                showSelectedPic(mImage3);
                break;
        }
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //configure the bar chart
    private void configBarChart(){
        mBarChart.setNoDataText("正在加载数据");
        mBarChart.setNoDataTextColor(R.color.black);

        Description description = mBarChart.getDescription();
        description.setPosition(70,20);
        description.setTextAlign(Paint.Align.RIGHT);
        description.setText("");

        Legend legend = mBarChart.getLegend();
        legend.setEnabled(false);

        XAxis xaxis = mBarChart.getXAxis();
        xaxis.setDrawAxisLine(true);
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setLabelCount(2);
        xaxis.setGranularityEnabled(true);
        xaxis.setGranularity(10f);

        YAxis yAxisLeft = mBarChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);
        yAxisLeft.setLabelCount(5, false);
        yAxisLeft.setSpaceTop(10);

        YAxis yAxisRight = mBarChart.getAxisRight();
        yAxisRight.setEnabled(false);

        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleYEnabled(false);
        mBarChart.setScaleXEnabled(true);
        mBarChart.setAutoScaleMinMaxEnabled(true);
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
        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
    }

    //find the view by the id
    private void findView(View root){
        mBarChart = root.findViewById(R.id.barChart);
        mWebView = root.findViewById(R.id.webView);
        mImage1 = root.findViewById(R.id.image1);
        mImage2 = root.findViewById(R.id.image2);
        mImage3 = root.findViewById(R.id.image3);
    }

    private void loadWebFile(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");

        mWebView.loadUrl("javascript:getRealTimeStream('ws://47.92.84.138:8599/123')");
    }

}
