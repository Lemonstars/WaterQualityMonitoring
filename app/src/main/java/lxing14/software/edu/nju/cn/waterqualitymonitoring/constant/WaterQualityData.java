package lxing14.software.edu.nju.cn.waterqualitymonitoring.constant;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/31
 * @time : 下午4:32
 */

public class WaterQualityData {

    public static final int TEMPERATURE = 0;
    public static final int ELECTRIC = 1;
    public static final int PH = 2;
    public static final int OXYGEN = 3;
    public static final int OXIDATION = 4;
    public static final int DIRTY = 5;
    public static final int TRANSPANENCY = 6;
    public static final int AMMONIA = 7;

    public static String getChineseName(int target){
        String res = "";
        switch (target){
            case TEMPERATURE:
                res = "温度";
                break;
            case ELECTRIC:
                res = "电导率";
                break;
            case PH:
                res = "ph值";
                break;
            case OXYGEN:
                res = "溶解氧";
                break;
            case OXIDATION:
                res = "氧化还原";
                break;
            case DIRTY:
                res = "浊度";
                break;
            case TRANSPANENCY:
                res = "透明度";
                break;
            case AMMONIA:
                res = "氨氮";
                break;
        }
        return res;
    }

    public static String getName(int target){
        String res = "";
        switch (target){
            case TEMPERATURE:
                res = "temperature";
                break;
            case ELECTRIC:
                res = "conductivity";
                break;
            case PH:
                res = "ph";
                break;
            case OXYGEN:
                res = "dissolvedOxygen";
                break;
            case OXIDATION:
                res = "redox";
                break;
            case DIRTY:
                res = "turbidity";
                break;
            case TRANSPANENCY:
                res = "transparency";
                break;
            case AMMONIA:
                res = "nh3";
                break;
        }
        return res;
    }

}
