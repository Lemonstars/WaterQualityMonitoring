package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login.LoginActivity;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_NAME = "userName";
    public static final String USER_AVATAR = "userAvatar";

    private MapContract.Presenter presenter;

    private TextView mUserName_tv;
    private ViewGroup mExit_layout;
    private ViewGroup mWaterLevelLayout;
    private ViewGroup mWaterQualityLayout;
    private ViewGroup mWaterFlowLayout;
    private ViewGroup mFloatingLayout;
    private ViewGroup mBoatLayout;
    private ViewGroup mRecordLayout;
    private ViewGroup mWaveLayout;
    private ViewGroup mWeatherLayout;

    public static Intent generateIntent(Context context, String userName){
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(USER_NAME, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        findView();
        getIntentData();
        configListener();

        // generate the view and the presenter
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (mapFragment == null) {
            mapFragment = MapFragment.generateFragment();

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    mapFragment, R.id.contentFrame);
        }

        presenter = new MapPresenter(mapFragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.waterLevel_layout:
                presenter.loadWaterLevelInfo();
                break;
            case R.id.waterQuality_layout:
                presenter.loadWaterQualityInfo();
                break;
            case R.id.waterFlow_layout:
                presenter.loadWaterFlowInfo();
                break;
            case R.id.wave_layout:
                presenter.loadWaveInfo();
                break;
            case R.id.weather_layout:
                presenter.loadWeatherInfo();
                break;
            case R.id.floating_layout:
                presenter.loadFloatingMaterialInfo();
                break;
            case R.id.boat_layout:
                presenter.loadUnmannedShipInfo();
                break;
            case R.id.record_layout:
                presenter.loadHisRecordInfo();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //set the listener
    private void configListener(){
        mExit_layout.setOnClickListener(
                e -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(SharePreferencesConstant.USER_NAME);
                    editor.remove(SharePreferencesConstant.PASSWORD);
                    editor.clear();
                    editor.apply();

                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });

        mWaterLevelLayout.setOnClickListener(this);
        mWaterQualityLayout.setOnClickListener(this);
        mFloatingLayout.setOnClickListener(this);
        mBoatLayout.setOnClickListener(this);
        mWaterFlowLayout.setOnClickListener(this);
        mRecordLayout.setOnClickListener(this);
        mWaveLayout.setOnClickListener(this);
        mWeatherLayout.setOnClickListener(this);
    }

    //get the data of the intent
    private void getIntentData(){
        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER_NAME);
        mUserName_tv.setText(userName);
    }

    private void findView(){
        mUserName_tv = findViewById(R.id.username_tv);
        mExit_layout = findViewById(R.id.exit_layout);
        mWaterLevelLayout = findViewById(R.id.waterLevel_layout);
        mWaterQualityLayout = findViewById(R.id.waterQuality_layout);
        mFloatingLayout = findViewById(R.id.floating_layout);
        mBoatLayout = findViewById(R.id.boat_layout);
        mWaterFlowLayout = findViewById(R.id.waterFlow_layout);
        mRecordLayout = findViewById(R.id.record_layout);
        mWaveLayout = findViewById(R.id.wave_layout);
        mWeatherLayout = findViewById(R.id.weather_layout);
    }

}
