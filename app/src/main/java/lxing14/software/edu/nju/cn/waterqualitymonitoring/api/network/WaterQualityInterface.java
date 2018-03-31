package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

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

}
