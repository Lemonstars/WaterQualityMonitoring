package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:38
 */

public class WaterQualityTypeVO {

    private int type;
    private String num;

    public WaterQualityTypeVO(int type, String num) {
        this.type = type;
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public String getNum() {
        return num;
    }
}
