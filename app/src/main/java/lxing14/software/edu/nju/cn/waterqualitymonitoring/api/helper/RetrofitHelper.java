package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.UserInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 刘兴
 * @version 1.0
 * @date 05/02/18
 */

public class RetrofitHelper {

    private static final String API_BASE_URL = "http://www.hawkcloud.cn:8099/Intelligence_Water/";

    private static Retrofit retrofit;

    private static UserInterface userInterface;
    private static WaterInterface waterInterface;

    private static void initRetrofit(){
        OkHttpClient mClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LogInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient).build();
    }

    public static UserInterface getUserInterface(){
        if(userInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            userInterface = retrofit.create(UserInterface.class);
        }
        return userInterface;
    }

    public static WaterInterface getWaterInterface(){
        if(waterInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterInterface = retrofit.create(WaterInterface.class);
        }
        return waterInterface;
    }

}
