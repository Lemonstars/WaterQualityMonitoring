package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;


import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterMapInfoVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/11
 * @time : 下午2:01
 */

public interface WaterInterface {

    @GET("water/queryAllWaterStation/{type}")
    Observable<List<WaterMapInfoVO>> getMapInfo(@Path("type")int type);

    @GET("water/lastWaterRecordsNum/{siteId}/{days}")
    Observable<List<WaterLevelVO>> getWaterLevelInfo(@Path("siteId")int siteId, @Path("days")int days);

    @GET("water/lastWater/{siteId}")
    Observable<WaterLevelVO> getCurrentWaterLevelInfo(@Path("siteId")int siteId);

}
