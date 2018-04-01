package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午7:02
 */

public class WaterStationInfoVO {

    private boolean hasWaterLevel;
    private boolean hasWaterFlow;
    private boolean hasFloatingMaterial;
    private boolean hasWaterQuality;
    private boolean hasUnmannedShip;

    public boolean isHasWaterLevel() {
        return hasWaterLevel;
    }

    public void setHasWaterLevel(boolean hasWaterLevel) {
        this.hasWaterLevel = hasWaterLevel;
    }

    public boolean isHasWaterFlow() {
        return hasWaterFlow;
    }

    public void setHasWaterFlow(boolean hasWaterFlow) {
        this.hasWaterFlow = hasWaterFlow;
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

    public boolean isHasUnmannedShip() {
        return hasUnmannedShip;
    }

    public void setHasUnmannedShip(boolean hasUnmannedShip) {
        this.hasUnmannedShip = hasUnmannedShip;
    }
}
