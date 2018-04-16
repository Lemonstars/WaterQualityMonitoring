package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午7:02
 */

public class WaterStationInfoVO {

    private int id;
    private String x;
    private String y;
    private String stnName;
    private String stnType; // 1水位 2水质 3流量 4漂浮物 5气象 6波浪 7历史数据 8无人船
    private String picUrl;
    private String province;
    private String city;
    private String stnAddr;
    private String stnCode;
    private String institute;
    private String stnNote;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public String getStnType() {
        return stnType;
    }

    public void setStnType(String stnType) {
        this.stnType = stnType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getStnNote() {
        return stnNote;
    }

    public void setStnNote(String stnNote) {
        this.stnNote = stnNote;
    }
}
