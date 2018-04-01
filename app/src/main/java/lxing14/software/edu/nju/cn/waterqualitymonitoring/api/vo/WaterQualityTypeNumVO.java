package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/1
 * @time : 下午4:37
 */

public class WaterQualityTypeNumVO {

    private String collectionTime;
    private String returnDateValue;
    private int id;

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getReturnDateValue() {
        return returnDateValue;
    }

    public void setReturnDateValue(String returnDateValue) {
        this.returnDateValue = returnDateValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
