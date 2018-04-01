package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

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

    @GET("waterStation/stationInfo/{stnId}")
    Observable<WaterStationInfoVO> getStationInfo(@Path("stnId")int stnId);

}
