package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterQualityActivity extends AppCompatActivity {

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterQuality);

        //pop the date picker
        ImageView date_iv = findViewById(R.id.date_iv);
        date_iv.setOnClickListener(e -> showCustomTimePicker());

        //generate the view and the presenter
        WaterQualityFragment waterQualityFragment = (WaterQualityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (waterQualityFragment == null) {
            waterQualityFragment = WaterQualityFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    waterQualityFragment, R.id.contentFrame);
        }

        new WaterQualityPresenter(waterQualityFragment);
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
