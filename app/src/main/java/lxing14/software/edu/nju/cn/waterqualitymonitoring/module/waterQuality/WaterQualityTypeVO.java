package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:38
 */

public class WaterQualityTypeVO {



    private int type;
    private String num;
    private String name;
    private int icon_pic_id;
    private int bg_id;

    public WaterQualityTypeVO(int type, String num) {
        this.type = type;
        this.num = num;

        switch (type){
            case WaterQualityData.TEMPERATURE:
                name = "温度";
                icon_pic_id = R.drawable.ic_temperature;
                bg_id = R.drawable.bg_temperature;
                break;
            case WaterQualityData.ELECTRIC:
                name = "电导率";
                icon_pic_id = R.drawable.ic_electric;
                bg_id = R.drawable.bg_electric;
                break;
            case WaterQualityData.PH:
                name = "/PH值";
                icon_pic_id = R.drawable.ic_ph;
                bg_id = R.drawable.bg_ph;
                break;
            case WaterQualityData.OXYGEN:
                name = "溶解氧";
                icon_pic_id = R.drawable.ic_oxygen;
                bg_id = R.drawable.bg_oxygen;
                break;
            case WaterQualityData.OXIDATION:
                name = "氧化还原";
                icon_pic_id = R.drawable.ic_redox;
                bg_id = R.drawable.bg_oxidation;
                break;
            case WaterQualityData.DIRTY:
                name = "浊度";
                icon_pic_id = R.drawable.ic_dirty;
                bg_id = R.drawable.bg_dirty;
                break;
            case WaterQualityData.TRANSPANENCY:
                name = "透明度";
                icon_pic_id = R.drawable.ic_transparency;
                bg_id = R.drawable.bg_transparency;
                break;
            case WaterQualityData.AMMONIA:
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
