package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFloating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterFloatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_floating);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.floatingMaterial);

        WaterFloatingFragment waterFloatingFragment = (WaterFloatingFragment)getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(waterFloatingFragment == null){
            waterFloatingFragment = WaterFloatingFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), waterFloatingFragment, R.id.contentFrame);
        }

        new WaterFloatingPresenter(waterFloatingFragment);

    }
}
