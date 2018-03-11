package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

public class WaterQualityFragment extends Fragment implements WaterQualityContract.View{

    private WaterQualityContract.Presenter mPresenter;

    private RecyclerView mType_rv;
    private MapView mMapView;

    public static WaterQualityFragment generateFragment(){
        return new WaterQualityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_water_quality, container, false);

        mType_rv = (RecyclerView) root.findViewById(R.id.type_rv);
        initRecyclerView();
        mMapView = (MapView) root.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        return root;
    }

    @Override
    public void setPresenter(WaterQualityContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initRecyclerView(){
        WaterQualityRVAdapter adapter = new WaterQualityRVAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mType_rv.setAdapter(adapter);
        mType_rv.setLayoutManager(linearLayoutManager);

        WaterQualityTypeVO vo1 = new WaterQualityTypeVO(WaterQualityTypeVO.PH, 7);
        WaterQualityTypeVO vo2 = new WaterQualityTypeVO(WaterQualityTypeVO.TEMPERATURE, 10);
        WaterQualityTypeVO vo3 = new WaterQualityTypeVO(WaterQualityTypeVO.OXYGEN, 1.33);
        List<WaterQualityTypeVO> data = new ArrayList<>();
        data.add(vo1);
        data.add(vo2);
        data.add(vo3);
        adapter.setData(data);
    }

}
