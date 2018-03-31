package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:38
 */

public class WaterQualityTypeVO {

    public static final int TEMPERATURE = 0;
    public static final int ELECTRIC = 1;
    public static final int PH = 2;
    public static final int OXYGEN = 3;
    public static final int OXIDATION = 4;
    public static final int DIRTY = 5;
    public static final int TRANSPANENCY = 6;
    public static final int AMMONIA = 7;

    private int type;
    private String num;
    private String name;
    private int icon_pic_id;
    private int bg_id;

    public WaterQualityTypeVO(int type, String num) {
        this.type = type;
        this.num = num;

        switch (type){
            case TEMPERATURE:
                name = "温度";
                icon_pic_id = R.drawable.ic_temperature;
                bg_id = R.drawable.bg_temperature;
                break;
            case ELECTRIC:
                name = "电导率";
                icon_pic_id = R.drawable.ic_electric;
                bg_id = R.drawable.bg_electric;
                break;
            case PH:
                name = "/PH值";
                icon_pic_id = R.drawable.ic_ph;
                bg_id = R.drawable.bg_ph;
                break;
            case OXYGEN:
                name = "溶解氧";
                icon_pic_id = R.drawable.ic_oxygen;
                bg_id = R.drawable.bg_oxygen;
                break;
            case OXIDATION:
                name = "氧化还原";
                icon_pic_id = R.drawable.ic_redox;
                bg_id = R.drawable.bg_oxidation;
                break;
            case DIRTY:
                name = "浊度";
                icon_pic_id = R.drawable.ic_dirty;
                bg_id = R.drawable.bg_dirty;
                break;
            case TRANSPANENCY:
                name = "透明度";
                icon_pic_id = R.drawable.ic_transparency;
                bg_id = R.drawable.bg_transparency;
                break;
            case AMMONIA:
                name = "氨氮";
                icon_pic_id = R.drawable.ic_nh3;
                bg_id = R.drawable.bg_nh3;
                break;
        }
    }

    public String getNum() {
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
