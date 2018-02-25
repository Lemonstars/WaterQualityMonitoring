package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtils;

public class WaterLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level);

        //generate the view and the presenter
        WaterLevelFragment waterLevelFragment = (WaterLevelFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(waterLevelFragment == null){
            waterLevelFragment = WaterLevelFragment.generateFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    waterLevelFragment, R.id.contentFrame);
        }

        new WaterLevelPresenter(waterLevelFragment);
    }

}
