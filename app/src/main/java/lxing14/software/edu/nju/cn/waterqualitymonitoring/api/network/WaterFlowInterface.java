package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.WaterFlowVO;
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

    @GET("flow/lastFlowRecordsNum/{id}/{num}")
    Observable<List<WaterFlowVO>> getLatestWaterFlow(@Path("id")int id, @Path("num")int num);

    @GET("flow/queryFlowByStdIdTime/{id}/{startTime}/{endTime}")
    Observable<List<WaterFlowVO>> getWaterFlowByDate(@Path("id")int id, @Path("startTime")String startTime, @Path("endTime")String endTime);

}
