package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class RecordActivity extends AppCompatActivity {

    public static Intent generateIntent(Context context, int stnId){
        Intent intent = new Intent(context, RecordActivity.class);
        intent.putExtra(CommonConstant.STN_ID, stnId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        //get the data of the intent
        Intent intent = getIntent();
        int stnId = intent.getIntExtra(CommonConstant.STN_ID, -1);

        //set the text of the toolbar
        TextView toolBarTV = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        String stationName = sharedPreferences.getString(SharePreferencesConstant.STATION_NAME, "");
        toolBarTV.setText(stationName +"-"+getString(R.string.record));

        //hide the date picker
        ImageView date_iv = findViewById(R.id.date_iv);
        date_iv.setVisibility(View.GONE);

        //generate the view and the presenter
        RecordFragment recordFragment = (RecordFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(recordFragment == null){
            recordFragment = RecordFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    recordFragment, R.id.contentFrame);
        }

        new RecordPresenter(recordFragment, stnId);
    }
}
