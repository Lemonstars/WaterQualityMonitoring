package lxing14.software.edu.nju.cn.waterqualitymonitoring.constant;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

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
    public static final int SALINITY = 8;
    public static final int TDS = 9;
    public static final int DENSITY =10;
    public static final int DO_SATURATION =11;
    public static final int TSS =12;
    public static final int CHLOROPHY =13;
    public static final int PHYCOCYANOBILIN =14;
    public static final int HATCH_OPEN = 15;
    public static final int WATER_PENETRATION =16;
    public static final int VOLTAGE = 17;

    private static final String[] NAME_ENGLISH =
            {
                    "temperature", "conductivity", "ph", "dissolvedOxygen",
                    "redox", "turbidity", "transparency", "nh3",
                    "salinity", "tds", "density", "doSaturation",
                    "tss", "chlorophylA", "phycocyanobilin", "hatchOpen",
                    "waterPenetration", "voltage"
            };

    private static final String[] NAME_CHINESE =
            {
                    "温度", "电导率", "PH值", "溶解氧",
                    "氧化还原", "浊度", "透明度", "氨氮",
                    "盐度", "矿化物", "水密度", "溶解氧饱和度",
                    "总悬浮物", "叶绿素a", "藻蓝素", "舱盖开",
                    "舱体进水", "电压"
            };

    private static final int[] PIC_RES_ID =
            {
                    R.drawable.ic_temperature, R.drawable.ic_electric, R.drawable.ic_ph, R.drawable.ic_oxygen,
                    R.drawable.ic_redox, R.drawable.ic_dirty, R.drawable.ic_transparency, R.drawable.ic_nh3,
                    R.drawable.ic_salinity, R.drawable.ic_tds, R.drawable.ic_density, R.drawable.ic_do_saturation,
                    R.drawable.ic_tss, R.drawable.ic_chlorophy_a, R.drawable.ic_phycocyanobilin, R.drawable.ic_hatch_open,
                    R.drawable.ic_water_penetration, R.drawable.ic_voltage
            };

    private static final int[] BG_RES_ID =
            {
                    R.drawable.bg_temperature, R.drawable.bg_electric, R.drawable.bg_ph, R.drawable.bg_oxygen,
                    R.drawable.bg_oxidation, R.drawable.bg_dirty, R.drawable.bg_transparency, R.drawable.bg_nh3,
                    R.drawable.bg_salinity, R.drawable.bg_tds, R.drawable.bg_density, R.drawable.bg_do_saturation,
                    R.drawable.bg_tss, R.drawable.bg_chlorophyl_a, R.drawable.bg_phycocyanobilin, R.drawable.bg_hatch_open,
                    R.drawable.bg_water_penetration, R.drawable.bg_voltage
            };


    public static String getEnglishName(int target){
         return NAME_ENGLISH[target];
    }

    public static String getChineseName(int target){
        return NAME_CHINESE[target];
    }

    public static int getPicResId(int target){
        return PIC_RES_ID[target];
    }

    public static int getBgResId(int target){
        return BG_RES_ID[target];
    }

}
