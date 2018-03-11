package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel.WaterLevelFragment;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel.WaterLevelPresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterQualityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality);

        //set the text of the toolbar
        TextView toolBarTV = (TextView)findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterQuality);

        //generate the view and the presenter
        WaterQualityFragment waterQualityFragment = (WaterQualityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(waterQualityFragment == null){
            waterQualityFragment = WaterQualityFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterQualityFragment, R.id.contentFrame);
        }

        new WaterQualityPresenter(waterQualityFragment);
    }
}
