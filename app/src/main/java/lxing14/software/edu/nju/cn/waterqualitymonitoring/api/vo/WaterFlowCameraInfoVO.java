package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

import java.util.List;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午2:28
 */

public class WaterFlowCameraInfoVO {

    private Integer stnId;
    private String collectionTime;
    private String instockTime;
    private String picOrVideo;
    private List<CameraVO> list;

    public Integer getStnId() {
        return stnId;
    }

    public void setStnId(Integer stnId) {
        this.stnId = stnId;
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

    public String getPicOrVideo() {
        return picOrVideo;
    }

    public void setPicOrVideo(String picOrVideo) {
        this.picOrVideo = picOrVideo;
    }

    public List<CameraVO> getList() {
        return list;
    }

    public void setList(List<CameraVO> list) {
        this.list = list;
    }
}
