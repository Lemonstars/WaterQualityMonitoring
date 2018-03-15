package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterLevel);

        //generate the view and the presenter
        WaterLevelFragment waterLevelFragment = (WaterLevelFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(waterLevelFragment == null){
            waterLevelFragment = WaterLevelFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterLevelFragment, R.id.contentFrame);
        }

        new WaterLevelPresenter(waterLevelFragment);
    }

}
