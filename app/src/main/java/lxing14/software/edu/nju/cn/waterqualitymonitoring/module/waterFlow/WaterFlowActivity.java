package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class WaterFlowActivity extends AppCompatActivity {

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_flow);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        toolBarTV.setText(R.string.waterFlow);

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

        new WaterFlowPresenter(waterFlowFragment);
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
