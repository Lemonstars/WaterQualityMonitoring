package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterFlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_flow);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterFlow);

        //generate the view and the presenter
        WaterFlowFragment waterFlowFragment = (WaterFlowFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (waterFlowFragment == null) {
            waterFlowFragment = WaterFlowFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterFlowFragment, R.id.contentFrame);
        }

        new WaterFlowPresenter(waterFlowFragment);

    }

}
