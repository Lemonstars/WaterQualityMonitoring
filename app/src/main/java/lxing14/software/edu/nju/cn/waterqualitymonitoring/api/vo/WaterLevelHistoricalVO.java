package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

import java.util.List;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/3
 * @time : 下午4:53
 */

public class WaterLevelHistoricalVO {

    /**
     * stnName : 前垾水文站
     * lastWaterLevel : 7.33
     * maxLevel : 6.97
     * minLevel : 0
     * picList : [{"url":"/pictures/QianHan/WaterL/2018-03-30/Camera06/173539_173555.jpeg","date":"2018-03-30 17:35:39.0"},{"url":"/pictures/QianHan/WaterL/2018-03-08/Camera06/2018-03-08 134606_134624.jpeg","date":"2018-03-08 13:46:06.0"},{"url":"/pictures/QianHan/WaterL/2018-03-09/Camera06/2018-03-09 093813_093827.jpeg","date":"2018-03-09 09:38:13.0"},{"url":"/pictures/QianHan/WaterL/2018-03-09/Camera06/2018-03-09 095347_095401.jpeg","date":"2018-03-09 09:53:47.0"}]
     */

    private String stnName;
    private double lastWaterLevel;
    private double maxLevel;
    private double minLevel;
    private List<PicListBean> picList;

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public double getLastWaterLevel() {
        return lastWaterLevel;
    }

    public void setLastWaterLevel(double lastWaterLevel) {
        this.lastWaterLevel = lastWaterLevel;
    }

    public double getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(double maxLevel) {
        this.maxLevel = maxLevel;
    }

    public double getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(double minLevel) {
        this.minLevel = minLevel;
    }

    public List<PicListBean> getPicList() {
        return picList;
    }

    public void setPicList(List<PicListBean> picList) {
        this.picList = picList;
    }

    public static class PicListBean {
        /**
         * url : /pictures/QianHan/WaterL/2018-03-30/Camera06/173539_173555.jpeg
         * date : 2018-03-30 17:35:39.0
         */

        private String url;
        private String date;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
