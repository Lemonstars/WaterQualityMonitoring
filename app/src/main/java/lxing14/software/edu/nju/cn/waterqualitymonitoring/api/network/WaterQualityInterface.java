package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/31
 * @time : 下午2:37
 */

public interface WaterQualityInterface {

    /**
     * 获取水质的最新值
     * @param siteId
     * @return
     */
    @GET("waterquality/lastWaterQuality/{siteId}")
    Observable<WaterQualityVO> getCurrentWaterQualityInfo(@Path("siteId")int siteId);


    /**
     * 获取水质在某个时间范围内的数据
     * @param siteId
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @GET("waterquality/lastWaterQualityByType/{siteId}/{param}/{startTime}/{endTime}")
    Observable<List<WaterQualityVO>> getWaterQualityInfo(@Path("siteId")int siteId, @Path("param")String type,
                                                         @Path("startTime")String startTime, @Path("endTime")String endTime);

    /**
     * 获取最近n天的数据
     * @param siteId
     * @param type
     * @param days
     * @return
     */
    @GET("waterquality/lastWaterQualityRecordsNum/{stnId}/{type}/{days}")
    Observable<List<WaterQualityVO>> getWaterQualityInfo(@Path("stnId")int stnId, @Path("type")String type, @Path("days")int days);

}
