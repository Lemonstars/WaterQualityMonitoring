package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class UnMannedShipActivity extends AppCompatActivity {

    public static Intent generateIntent(Context context, int stnId){
        Intent intent = new Intent(context, UnMannedShipActivity.class);
        intent.putExtra(CommonConstant.STN_ID, stnId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_manned_ship);

        //get the data of the intent
        Intent intent = getIntent();
        int stnId = intent.getIntExtra(CommonConstant.STN_ID, -1);

        //generate the view and the presenter
        UnMannedShipFragment unMannedShipFragment = (UnMannedShipFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(unMannedShipFragment == null){
            unMannedShipFragment = UnMannedShipFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), unMannedShipFragment, R.id.contentFrame);
        }

        new UnMannedShipPresenter(unMannedShipFragment, stnId);
    }
}
