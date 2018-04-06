package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class ChartActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String DATE_LIST = "dateList";
    public static final String DATA_LIST = "dataList";

    public static Intent generateIntent(Context context,String name, String startTime, String endTime,
                                        ArrayList<String> dateList, ArrayList<String> dataList){
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra(NAME, name);
        intent.putExtra(START_TIME, startTime);
        intent.putExtra(END_TIME, endTime);
        intent.putStringArrayListExtra(DATE_LIST, dateList);
        intent.putStringArrayListExtra(DATA_LIST, dataList);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_chart);

        //get the intent data
        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        String startTime = intent.getStringExtra(START_TIME);
        String endTime = intent.getStringExtra(END_TIME);
        ArrayList<String> dateList = intent.getStringArrayListExtra(DATE_LIST);
        ArrayList<String> dataList = intent.getStringArrayListExtra(DATA_LIST);
        ArrayList<Float> dataNumList = new ArrayList<>(dataList.size());
        for(String str: dataList){
            dataNumList.add(Float.parseFloat(str));
        }

        //initialize the view and the presenter
        ChartFragment chartFragment = (ChartFragment)getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if(chartFragment==null){
            chartFragment = ChartFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chartFragment, R.id.contentFrame);
        }

        new ChartPresenter(chartFragment,name, startTime, endTime, dateList, dataNumList);
    }


}
