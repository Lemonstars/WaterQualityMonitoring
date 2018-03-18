package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.amap.api.maps.MapView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.CameraChoiceView;

public class WaterFlowFragment extends Fragment implements WaterFlowContract.View {

    private WaterFlowContract.Presenter mPresenter;

    private MapView mMapView;
    private LinearLayout mCamera_layout;

    public static WaterFlowFragment generateFragment() {
        return new WaterFlowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_flow, container, false);

        mMapView = root.findViewById(R.id.map);
        mCamera_layout = root.findViewById(R.id.camera_layout);

        mMapView.onCreate(savedInstanceState);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void setPresenter(WaterFlowContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showCameraChoiceView(int num) {
        CameraChoiceView cameraChoiceView;
        for(int i=0;i<num;i++){
            char ch = (char)('A' + i);
            cameraChoiceView = new CameraChoiceView(getContext(), ch);
            mCamera_layout.addView(cameraChoiceView);
        }

    }
}
