package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.DateConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.TimeUtil;

public class WaterFlowActivity extends AppCompatActivity {

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;
    private WaterFlowContract.Presenter mPresenter;

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

        mPresenter = new WaterFlowPresenter(waterFlowFragment);
    }

    public void showCustomTimePicker() {

        if (mDoubleTimeSelectDialog == null) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(this, DateConstant.ALLOWED_SMALLEST_TIME, DateConstant.ALLOWED_BIGGEST_TIME, TimeUtil.getTodayDate());
            mDoubleTimeSelectDialog.setOnDateSelectFinished( (startTime, endTime) -> mPresenter.loadWaterLevelDataByDate(startTime, endTime));

            mDoubleTimeSelectDialog.setOnDismissListener(dialog -> dialog.dismiss());
        }
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }


}
