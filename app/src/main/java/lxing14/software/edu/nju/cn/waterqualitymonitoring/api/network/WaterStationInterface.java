package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.HistoryRecordVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterStationInfoVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午7:01
 */

public interface WaterStationInterface {

    @GET("water/queryAllWaterStation/0")
    Observable<List<WaterStationInfoVO>> getAllStationInfo();


    @GET("waterStation/historyInfo/{stnId}")
    Observable<HistoryRecordVO> getStationHistoryInfo(@Path("stnId")int stnId);

}
