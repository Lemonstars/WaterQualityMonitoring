package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/10
 * @time : 下午8:13
 */

public class WaterLevelVO {
    /**
     * picPath : /pictures/QianHan/WaterL/2018-03-10/Camera06/2018-03-10 135105_135123.jpeg
     * c_time : 2018-03-10 13:51:05.0
     * stnId : 1
     * id : 1164148
     * waterLevel : 7.74
     */

    private String picPath;
    private String c_time;
    private int stnId;
    private int id;
    private float waterLevel;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public int getStnId() {
        return stnId;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(float waterLevel) {
        this.waterLevel = waterLevel;
    }
}
