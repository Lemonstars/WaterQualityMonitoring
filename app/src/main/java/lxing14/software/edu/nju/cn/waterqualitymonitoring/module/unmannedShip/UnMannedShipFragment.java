package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.unmannedShip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class UnMannedShipFragment extends Fragment implements UnMannedShipContract.IView{

    private UnMannedShipContract.IPresenter mPresenter;

    public static UnMannedShipFragment generateFragment(){
        return new UnMannedShipFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_un_manned_ship, container, false);
        return root;
    }

    @Override
    public void setPresenter(UnMannedShipContract.IPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
