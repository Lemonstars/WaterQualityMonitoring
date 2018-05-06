package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.content.Context;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/5/6
 * @time : 下午7:32
 */

public class SizeUtil {

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

}
