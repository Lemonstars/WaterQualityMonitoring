package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author 刘兴
 * @version 1.0
 * @date 06/02/18
 */

public interface UserInterface {

    @GET("user/login/{userName}/{password}")
    Observable<String> login(@Path("userName") String userName, @Path("password") String password);

}
