package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class UnMannedShipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_manned_ship);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.unmannedShip);

        //generate the view and the presenter
        UnMannedShipFragment unMannedShipFragment = (UnMannedShipFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(unMannedShipFragment == null){
            unMannedShipFragment = UnMannedShipFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), unMannedShipFragment, R.id.contentFrame);
        }

        new UnMannedShipPresenter(unMannedShipFragment);

    }
}
