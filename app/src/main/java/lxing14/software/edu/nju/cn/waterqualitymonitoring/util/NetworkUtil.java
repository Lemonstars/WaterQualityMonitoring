package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午9:25
 */

public class NetworkUtil {

    public static boolean isNetworkAccessed(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (NetworkInfo networkInfo: net_info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
