package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/29
 * @time : 下午2:26
 */

public class TimeUtil {

    public static long convertToNum(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetDate = null;
        try {
            targetDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return targetDate==null? 0:targetDate.getTime();
    }

}
