package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.HistoryRecordVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.PdfVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.MapMarkerConfig;
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
        RetrofitHelper.getWaterStationInterface().getStationHistoryInfo(mStnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HistoryRecordVO>(mView.getContextView()) {
                    @Override
                    public void onNext(HistoryRecordVO vo) {
                        List<String> picList = vo.getPicList();
                        List<PdfVO> pdfVOList = vo.getPdfVOList();
                        if(picList!=null && picList.size()!=0){
                            mView.showPic(picList);
                        }else {
                            mView.hidePicLayout();
                        }

                        if(pdfVOList!=null&&vo.getPdfVOList().size()!=0){
                            mView.showPDFData(pdfVOList);
                        }else {
                            mView.hidePDFLayout();
                        }
                    }
                });
    }

    @Override
    public void loadAllStationInfo() {
        RetrofitHelper.getWaterStationInterface().getAllStationInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<WaterStationInfoVO>>(mView.getContextView()) {
                    @Override
                    public void onNext(List<WaterStationInfoVO> waterStationInfoVOS) {
                        new MapMarkerConfig(){
                            @Override
                            public void showStationLocation(ArrayList<MarkerOptions> list) {
                                mView.showStationLocation(list);
                            }
                        }.onRequestStationInfo(mView.getContextView(), waterStationInfoVOS);
                    }
                });
    }
}
