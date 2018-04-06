package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingByDateVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingPicVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFloatingVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午7:08
 */

public interface WaterFloatingInterface {

    /**
     * 根据日期范围获取漂浮物数据
     * @param siteId
     * @param startTime
     * @param endTime
     * @return
     */
    @GET("floating/queryRecordsNumByTime/{siteId}/{startTime}/{endTime}")
    Observable<List<WaterFloatingByDateVO>> getFloatingInfoByDate(@Path("siteId")int siteId, @Path("startTime")String startTime,
                                                                  @Path("endTime")String endTime);

    /**
     * 获取漂浮物图片url
     * @param siteId
     * @return
     */
    @GET("floating/queryLastRecord/{siteId}")
    Observable<List<WaterFloatingPicVO>> getFloatingInfoPic(@Path("siteId")int siteId);

}
