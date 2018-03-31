package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/31
 * @time : 下午2:39
 */

public class WaterQualityVO {

    private int id;
    private Integer stnId;              //站点编号  ***
    private String temperature;        //温度值     *** random的时候多参照几个区间
    private Double ph;                  //PH值      ***
    private Double dissolvedOxygen;    //溶解氧值    ***
    private Double redox;               //氧化还原值  ***
    private Double transparency;       //透明度值
    private Double conductivity;       //电导率
    private Double turbidity;          //浊度值
    private Double NH3;                 //氨氮值
    private String collectionTime;     //采集时间    ***
    private String instockTime;        //入库时间    ***
    private String state;               //状态编号（1为绿色，代表正常；2为灰色，代表机器问题或设备维护中没数据；3为红色，代表设备损坏）
    private String picOrVideo;         //文件是图片还是视频（p为图片；v为视频)
    private String FilePath;           //文件地址
    //private WaterStation waterStation; //站点对象
    //2018-03-14 增加竺山湾站点，所以增加以下字段
    private Double salinity;       //盐度
    private Double tds;       //矿化度
    private Double density;       //水密度',
    private Double doSaturation;       //溶解氧饱和度
    private Double tss;       //总悬浮物
    private Double chlorophylA;       //叶绿素a
    private Double phycocyanobilin ;       //藻蓝素
    private Double hatchOpen;       //舱盖开
    private Double waterPenetration;       //仓体进水
    private Double voltage ;       //电压

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStnId() {
        return stnId;
    }

    public void setStnId(Integer stnId) {
        this.stnId = stnId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public void setDissolvedOxygen(Double dissolvedOxygen) {
        this.dissolvedOxygen = dissolvedOxygen;
    }

    public Double getRedox() {
        return redox;
    }

    public void setRedox(Double redox) {
        this.redox = redox;
    }

    public Double getTransparency() {
        return transparency;
    }

    public void setTransparency(Double transparency) {
        this.transparency = transparency;
    }

    public Double getConductivity() {
        return conductivity;
    }

    public void setConductivity(Double conductivity) {
        this.conductivity = conductivity;
    }

    public Double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(Double turbidity) {
        this.turbidity = turbidity;
    }

    public Double getNH3() {
        return NH3;
    }

    public void setNH3(Double NH3) {
        this.NH3 = NH3;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getInstockTime() {
        return instockTime;
    }

    public void setInstockTime(String instockTime) {
        this.instockTime = instockTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPicOrVideo() {
        return picOrVideo;
    }

    public void setPicOrVideo(String picOrVideo) {
        this.picOrVideo = picOrVideo;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public Double getSalinity() {
        return salinity;
    }

    public void setSalinity(Double salinity) {
        this.salinity = salinity;
    }

    public Double getTds() {
        return tds;
    }

    public void setTds(Double tds) {
        this.tds = tds;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Double getDoSaturation() {
        return doSaturation;
    }

    public void setDoSaturation(Double doSaturation) {
        this.doSaturation = doSaturation;
    }

    public Double getTss() {
        return tss;
    }

    public void setTss(Double tss) {
        this.tss = tss;
    }

    public Double getChlorophylA() {
        return chlorophylA;
    }

    public void setChlorophylA(Double chlorophylA) {
        this.chlorophylA = chlorophylA;
    }

    public Double getPhycocyanobilin() {
        return phycocyanobilin;
    }

    public void setPhycocyanobilin(Double phycocyanobilin) {
        this.phycocyanobilin = phycocyanobilin;
    }

    public Double getHatchOpen() {
        return hatchOpen;
    }

    public void setHatchOpen(Double hatchOpen) {
        this.hatchOpen = hatchOpen;
    }

    public Double getWaterPenetration() {
        return waterPenetration;
    }

    public void setWaterPenetration(Double waterPenetration) {
        this.waterPenetration = waterPenetration;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }
}
