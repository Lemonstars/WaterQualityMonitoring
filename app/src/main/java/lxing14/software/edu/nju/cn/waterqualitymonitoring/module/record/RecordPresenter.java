package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.PdfVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/13
 * @time : 上午10:41
 */

public class RecordPresenter implements RecordContract.Presenter {

    private RecordContract.View mView;
    private int mStnId;

    public RecordPresenter(RecordContract.View view, int stnId) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.mStnId = stnId;
    }

    @Override
    public void loadRecordData() {
        mView.showPic(null);

        List<PdfVO> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            PdfVO vo = new PdfVO();
            vo.setName("water.pdf");
            vo.setTime("2018-4-1 10:00");
            vo.setNote("");
            vo.setUrl("www.baidu.com");
            list.add(vo);
        }
        mView.showPDFData(list);
    }

    @Override
    public void loadAllStationInfo() {
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterStationInfoVO> waterStationInfoVOS) {
                        LatLng latLng;
                        ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>(waterStationInfoVOS.size());
                        for(WaterStationInfoVO vo: waterStationInfoVOS){
                            double x = Double.parseDouble(vo.getX());
                            double y = Double.parseDouble(vo.getY());

                            latLng = new LatLng(y, x);
                            MarkerOptions markerOptions = new MarkerOptions();
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.valueOf(vo.getId()));
                            sb.append(' ');
                            sb.append(vo.getName());
                            sb.append(' ');
                            sb.append(vo.isHasWaterLevel()? 1:0); // water level
                            sb.append(' ');
                            sb.append(vo.isHasWaterQuality()? 1:0); // water quality
                            sb.append(' ');
                            sb.append(vo.isHasWaterFlow()? 1:0); // water flow
                            sb.append(' ');
                            sb.append(vo.isHasFloatingMaterial()? 1:0); // floating
                            sb.append(' ');
                            sb.append(vo.isHasUnmannedShip()? 1:0); // boat
                            sb.append(' ');
                            sb.append(y);
                            sb.append(' ');
                            sb.append(x);

                            markerOptions.position(latLng).snippet(sb.toString());
                            markerOptionsArrayList.add(markerOptions);
                        }

                        mView.showStationLocation(markerOptionsArrayList);
                    }
                });
    }
}
