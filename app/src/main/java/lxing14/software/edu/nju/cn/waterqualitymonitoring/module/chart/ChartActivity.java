package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.chart;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_chart);

        //initialize the view and the presenter
        ChartFragment chartFragment = (ChartFragment)getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if(chartFragment==null){
            chartFragment = ChartFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chartFragment, R.id.contentFrame);
        }

        new ChartPresenter(chartFragment);
    }


}
