package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/29
 * @time : 下午2:26
 */

public class TimeUtil {

    public static long convertToNum(String date){
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        Date targetDate = null;
        try {
            targetDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return targetDate==null? 0:targetDate.getTime();
    }

    /**
     * 获取当天的日期
     * @return
     */
    public static String getTodayDate(){
        Date date = new Date();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getCurrentTime(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取前n天d的日期
     * @param num
     * @return
     */
    public static String getDateBeforeNum(int num){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -num);
        return format.format(c.getTime());
    }

}
