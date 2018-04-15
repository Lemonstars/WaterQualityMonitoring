package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午7:02
 */

public class WaterStationInfoVO {


    /**
     * hasWaterFlow : true
     * hasWeather : false
     * hasFloatingMaterial : true
     * hasWaterQuality : true
     * hasWave : false
     * name : 前垾水文站
     * x : 118.87056
     * hasWaterLevel : true
     * y : 31.864978
     * hasHistoryRecord : false
     * id : 1
     * hasUnmannedShip : false
     */

    private boolean hasWaterFlow;
    private boolean hasWeather;
    private boolean hasFloatingMaterial;
    private boolean hasWaterQuality;
    private boolean hasWave;
    private boolean hasWaterLevel;
    private boolean hasUnmannedShip;
    private boolean hasHistoryRecord;
    private String name;
    private String x;
    private String y;
    private int id;

    public boolean isHasWaterFlow() {
        return hasWaterFlow;
    }

    public void setHasWaterFlow(boolean hasWaterFlow) {
        this.hasWaterFlow = hasWaterFlow;
    }

    public boolean isHasWeather() {
        return hasWeather;
    }

    public void setHasWeather(boolean hasWeather) {
        this.hasWeather = hasWeather;
    }

    public boolean isHasFloatingMaterial() {
        return hasFloatingMaterial;
    }

    public void setHasFloatingMaterial(boolean hasFloatingMaterial) {
        this.hasFloatingMaterial = hasFloatingMaterial;
    }

    public boolean isHasWaterQuality() {
        return hasWaterQuality;
    }

    public void setHasWaterQuality(boolean hasWaterQuality) {
        this.hasWaterQuality = hasWaterQuality;
    }

    public boolean isHasWave() {
        return hasWave;
    }

    public void setHasWave(boolean hasWave) {
        this.hasWave = hasWave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public boolean isHasWaterLevel() {
        return hasWaterLevel;
    }

    public void setHasWaterLevel(boolean hasWaterLevel) {
        this.hasWaterLevel = hasWaterLevel;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public boolean isHasHistoryRecord() {
        return hasHistoryRecord;
    }

    public void setHasHistoryRecord(boolean hasHistoryRecord) {
        this.hasHistoryRecord = hasHistoryRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasUnmannedShip() {
        return hasUnmannedShip;
    }

    public void setHasUnmannedShip(boolean hasUnmannedShip) {
        this.hasUnmannedShip = hasUnmannedShip;
    }
}
