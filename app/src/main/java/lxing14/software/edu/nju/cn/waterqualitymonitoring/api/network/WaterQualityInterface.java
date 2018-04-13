package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.UnmannedBoatVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterQualityTypeNumVO;
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
    Observable<List<WaterQualityTypeNumVO>> getWaterQualityInfo(@Path("siteId")int siteId, @Path("param")String type,
                                                         @Path("startTime")String startTime, @Path("endTime")String endTime);

    /**
     * 获取无人船采集的最新水质信息和当前经纬度坐标
     * @param stnId
     * @return
     */
    @GET("waterquality/UnmannedBoat/{stnId}")
    Observable<UnmannedBoatVO> getUnmannedBoatInfo(@Path("stnId")int stnId);


    /**
     * 获取无人船采集的历史水质信息和当前经纬度坐标
     * @param stnId
     * @return
     */
    @GET("waterquality/UnmannedBoatHisInfo/{stnId}")
    Observable<List<UnmannedBoatVO>> getUnmannedBoatHisInfo(@Path("stnId")int stnId);

}
