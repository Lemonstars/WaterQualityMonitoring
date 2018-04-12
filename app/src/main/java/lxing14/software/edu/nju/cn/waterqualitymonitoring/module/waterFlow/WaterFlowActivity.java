package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.DateConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;

public class WaterFlowActivity extends AppCompatActivity {

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;
    private WaterFlowContract.Presenter mPresenter;

    public static Intent generateIntent(Context context, int stnId){
        Intent intent = new Intent(context, WaterFlowActivity.class);
        intent.putExtra(CommonConstant.STN_ID, stnId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_flow);

        //get the data of the intent
        Intent intent = getIntent();
        int stnId = intent.getIntExtra(CommonConstant.STN_ID, -1);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        String stationName = sharedPreferences.getString(SharePreferencesConstant.STATION_NAME, "");
        toolBarTV.setText(stationName +"-"+getString(R.string.waterFlow));

        //pop the date picker
        ImageView date_iv = findViewById(R.id.date_iv);
        date_iv.setOnClickListener(e -> showCustomTimePicker());

        //generate the view and the presenter
        WaterFlowFragment waterFlowFragment = (WaterFlowFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (waterFlowFragment == null) {
            waterFlowFragment = WaterFlowFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterFlowFragment, R.id.contentFrame);
        }

        mPresenter = new WaterFlowPresenter(waterFlowFragment, stnId);
    }

    public void showCustomTimePicker() {

        if (mDoubleTimeSelectDialog == null) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(this, DateConstant.ALLOWED_SMALLEST_TIME, DateConstant.ALLOWED_BIGGEST_TIME, TimeUtil.getTodayDate());
            mDoubleTimeSelectDialog.setOnDateSelectFinished( (startTime, endTime) -> mPresenter.loadWaterFlowDataByDate(startTime, endTime));

            mDoubleTimeSelectDialog.setOnDismissListener(dialog -> dialog.dismiss());
        }
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }


}
