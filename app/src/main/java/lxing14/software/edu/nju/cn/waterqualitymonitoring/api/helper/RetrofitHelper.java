package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.UserInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterFloatingInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterFlowInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterLevelInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterQualityInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.network.WaterStationInterface;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WebSite;
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
    private static Retrofit retrofit;

    private static UserInterface userInterface;
    private static WaterFlowInterface waterFlowInterface;
    private static WaterLevelInterface waterLevelInterface;
    private static WaterStationInterface waterStationInterface;
    private static WaterQualityInterface waterQualityInterface;
    private static WaterFloatingInterface waterFloatingInterface;

    private static void initRetrofit(){
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addNetworkInterceptor(new LogInterceptor())
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(WebSite.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient).build();
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

    public static WaterFlowInterface getWaterFlowInterface(){
        if(waterFlowInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterFlowInterface = retrofit.create(WaterFlowInterface.class);
        }
        return waterFlowInterface;
    }

    public static WaterLevelInterface getWaterInterface(){
        if(waterLevelInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterLevelInterface = retrofit.create(WaterLevelInterface.class);
        }
        return waterLevelInterface;
    }

    public static WaterStationInterface getWaterStationInterface(){
        if(waterStationInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterStationInterface = retrofit.create(WaterStationInterface.class);
        }

        return waterStationInterface;
    }

    public static WaterQualityInterface getWaterQualityInterface(){
        if(waterQualityInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterQualityInterface = retrofit.create(WaterQualityInterface.class);
        }

        return waterQualityInterface;
    }

    public static WaterFloatingInterface getWaterFloatingInterface(){
        if(waterFloatingInterface == null){
            if(retrofit == null){
                initRetrofit();
            }
            waterFloatingInterface = retrofit.create(WaterFloatingInterface.class);
        }

        return waterFloatingInterface;
    }

}
