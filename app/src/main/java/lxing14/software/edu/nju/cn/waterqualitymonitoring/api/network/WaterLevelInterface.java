package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;


import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterLevelVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/11
 * @time : 下午2:01
 */

public interface WaterLevelInterface {

    /**
     * 根据条目数获取水位数据
     * @param siteId
     * @param days
     * @return
     */
    @GET("water/lastWaterRecordsNum/{siteId}/{days}")
    Observable<List<WaterLevelVO>> getWaterLevelByNum(@Path("siteId")int siteId, @Path("days")int days);

    /**
     * 根据日期区间获取水位数据
     * @param stnId
     * @param startTime
     * @param endTime
     * @return
     */
    @GET("water/hisWater/{stnId}/{startTime}/{endTime}")
    Observable<List<WaterLevelVO>> getWaterLevelByDate(@Path("stnId")int stnId,
                                                       @Path("startTime")String startTime, @Path("endTime")String endTime);

    @GET("water/lastWater/{siteId}")
    Observable<WaterLevelVO> getCurrentWaterLevelInfo(@Path("siteId")int siteId);

}
