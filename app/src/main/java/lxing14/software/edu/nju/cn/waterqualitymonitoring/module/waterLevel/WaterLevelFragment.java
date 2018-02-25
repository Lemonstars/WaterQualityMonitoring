package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterLevel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class WaterLevelFragment extends Fragment implements WaterLevelContract.View{

    private WaterLevelContract.Presenter presenter;

    public static WaterLevelFragment generateFragment(){
        return new WaterLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_water_level, container, false);
    }

    @Override
    public void setPresenter(WaterLevelContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
