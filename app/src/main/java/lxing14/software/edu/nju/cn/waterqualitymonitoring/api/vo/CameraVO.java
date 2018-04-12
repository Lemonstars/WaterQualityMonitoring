package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

public class CameraVO {

    private Integer id;
    private Integer stnId;
    private Integer cameraNo;
    private Double speed;
    private Double flow;
    private Integer state;
    private String FilePath;
    private String collectionTime;
    private String instockTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStnId() {
        return stnId;
    }

    public void setStnId(Integer stnId) {
        this.stnId = stnId;
    }

    public Integer getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(Integer cameraNo) {
        this.cameraNo = cameraNo;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
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
}