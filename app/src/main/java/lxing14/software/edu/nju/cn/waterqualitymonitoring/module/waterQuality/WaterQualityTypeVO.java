package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:38
 */

public class WaterQualityTypeVO {

    public static final int PH = 0;
    public static final int TEMPERATURE = 1;
    public static final int TRANSPANENCY = 2;
    public static final int OXYGEN = 3;
    public static final int REDOX = 4;

    private int type;
    private double num;
    private String name;
    private int icon_pic_id;
    private int bg_id;

    public WaterQualityTypeVO(int type, double num) {
        this.type = type;
        this.num = num;

        switch (type){
            case PH:
                name = "/PH值";
                icon_pic_id = R.drawable.ic_ph;
                bg_id = R.drawable.bg_ph;
                break;
            case TEMPERATURE:
                name = "温度";
                icon_pic_id = R.drawable.ic_temperature;
                bg_id = R.drawable.bg_temperature;
                break;
            case TRANSPANENCY:
                name = "透明度";
                icon_pic_id = R.drawable.ic_transparency;
                bg_id = R.drawable.bg_transparency;
                break;
            case OXYGEN:
                name = "溶解氧气";
                icon_pic_id = R.drawable.ic_oxygen;
                bg_id = R.drawable.bg_ph;
                break;
            case REDOX:
                name = "氧化还原电位";
                icon_pic_id = R.drawable.ic_redox;
                bg_id = R.drawable.bg_temperature;
                break;
        }
    }

    public int getType() {
        return type;
    }

    public double getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public int getIcon_pic_id() {
        return icon_pic_id;
    }

    public int getBg_id() {
        return bg_id;
    }
}
