package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowCameraInfoVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVideoUrlVO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午8:27
 */

public interface WaterFlowInterface {

    /**
     * 获取最近n条流量数据
     * @param id
     * @param num
     * @return
     */
    @GET("flow/lastFlowRecordsNum/{id}/{num}")
    Observable<List<WaterFlowVO>> getLatestWaterFlow(@Path("id")int id, @Path("num")int num);

    /**
     * 根据时间范围获取流量数据
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    @GET("flow/queryFlowByStdIdTime/{id}/{startTime}/{endTime}")
    Observable<List<WaterFlowVO>> getWaterFlowByDate(@Path("id")int id, @Path("startTime")String startTime, @Path("endTime")String endTime);

    /**
     * 获取视频的url
     * @param stnId
     * @return
     */
    @GET("flow/video/{stnId}/flow")
    Observable<List<WaterFlowVideoUrlVO>> getVideoUrl(@Path("stnId")int stnId);


    /**
     * 获取相机信息
     * @param stnId
     * @return
     */
    @GET("speedflow/queryLastSpeedFlowByStdId/{stnId}")
    Observable<List<WaterFlowCameraInfoVO>> getCameraInfo(@Path("stnId")int stnId);

}
