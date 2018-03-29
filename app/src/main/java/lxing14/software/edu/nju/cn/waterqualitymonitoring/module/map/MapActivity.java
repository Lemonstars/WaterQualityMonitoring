package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USER_NAME = "userName";
    public static final String USER_AVATAR = "userAvatar";

    private MapContract.Presenter presenter;

    private TextView mUserName_tv;

    public static Intent generateIntent(Context context, String userName){
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(USER_NAME, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //initialize the navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View head = navigationView.inflateHeaderView(R.layout.nav_header_map);
        mUserName_tv = head.findViewById(R.id.username_tv);

        getIntentData();

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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_waterLevel) {
            presenter.loadWaterLevelInfo();
        } else if (id == R.id.nav_waterForce) {
            presenter.loadWaterForceInfo();
        } else if (id == R.id.nav_waterQuality) {
            presenter.loadWaterQualityInfo();
        } else if (id == R.id.nav_floatingMaterial) {
            presenter.loadFloatingMaterialInfo();
        } else if (id == R.id.nav_unmannedShip){
            presenter.loadUnmannedShipInfo();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getIntentData(){
        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER_NAME);
        mUserName_tv.setText(userName);
    }

}
