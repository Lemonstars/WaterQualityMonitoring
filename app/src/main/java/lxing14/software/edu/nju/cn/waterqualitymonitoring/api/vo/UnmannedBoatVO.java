package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/12
 * @time : 下午4:09
 */

public class UnmannedBoatVO {

    private int stnId;
    private double longitude;
    private double latitude;
    private String temperature;
    private double ph;
    private double dissolvedOxygen;
    private double redox;

    public int getStnId() {
        return stnId;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public void setDissolvedOxygen(double dissolvedOxygen) {
        this.dissolvedOxygen = dissolvedOxygen;
    }

    public double getRedox() {
        return redox;
    }

    public void setRedox(double redox) {
        this.redox = redox;
    }
}
