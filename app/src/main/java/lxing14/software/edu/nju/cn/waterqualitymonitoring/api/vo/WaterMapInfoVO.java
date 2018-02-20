package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/2/11
 * @time : 下午2:04
 */

public class WaterMapInfoVO {

    private String picUrl;
    private String stnType;
    private String province;
    private String city;
    private String stnAddr;
    private String stnCode;
    private String x;
    private String stnName;
    private String y;
    private String institute;
    private int id;
    private String stnNote;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStnType() {
        return stnType;
    }

    public void setStnType(String stnType) {
        this.stnType = stnType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStnAddr() {
        return stnAddr;
    }

    public void setStnAddr(String stnAddr) {
        this.stnAddr = stnAddr;
    }

    public String getStnCode() {
        return stnCode;
    }

    public void setStnCode(String stnCode) {
        this.stnCode = stnCode;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStnNote() {
        return stnNote;
    }

    public void setStnNote(String stnNote) {
        this.stnNote = stnNote;
    }

    @Override
    public String toString() {
        return "WaterMapInfoVO{" +
                "picUrl='" + picUrl + '\'' +
                ", stnType='" + stnType + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", stnAddr='" + stnAddr + '\'' +
                ", stnCode='" + stnCode + '\'' +
                ", x='" + x + '\'' +
                ", stnName='" + stnName + '\'' +
                ", y='" + y + '\'' +
                ", institute='" + institute + '\'' +
                ", id=" + id +
                ", stnNote='" + stnNote + '\'' +
                '}';
    }
}
