package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterLevelActivity extends AppCompatActivity {

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;
    private double mLatitude;
    private double mLongtide;

    public static Intent generateIntent(Context context, double latitude, double longitude ){
        Intent intent = new Intent(context, WaterLevelActivity.class);
        intent.putExtra(CommonConstant.LATITUDE, latitude);
        intent.putExtra(CommonConstant.LONGITUDE, longitude);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level);

        //get the data of the intent
        getIntentData();

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterLevel);

        //pop the date picker
        ImageView date_iv = findViewById(R.id.date_iv);
        date_iv.setOnClickListener(e -> showCustomTimePicker());

        //generate the view and the presenter
        WaterLevelFragment waterLevelFragment = (WaterLevelFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(waterLevelFragment == null){
            waterLevelFragment = WaterLevelFragment.generateFragment(mLatitude, mLongtide);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterLevelFragment, R.id.contentFrame);
        }

        new WaterLevelPresenter(waterLevelFragment);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra(CommonConstant.LATITUDE, CommonConstant.LATITUDE_OF_BJ);
        mLongtide = intent.getDoubleExtra(CommonConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_BJ);
    }

    public void showCustomTimePicker() {
        String allowedSmallestTime = "2017-1-1";
        String allowedBiggestTime = "2020-12-31";
        String defaultChooseDate = "2018-01-18";

        if (mDoubleTimeSelectDialog == null) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(this, allowedSmallestTime, allowedBiggestTime, defaultChooseDate);
            mDoubleTimeSelectDialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
                @Override
                public void onSelectFinished(String startTime, String endTime) {

                }
            });

            mDoubleTimeSelectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
        }
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }

}
