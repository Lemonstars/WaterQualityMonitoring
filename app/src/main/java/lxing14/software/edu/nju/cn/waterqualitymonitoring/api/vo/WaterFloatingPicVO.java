package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午7:53
 */

public class WaterFloatingPicVO {


    /**
     * filePathResult : /njqh_pictures/floatingmatter/2018/1_r.jpg
     * stnId : 1
     * id : 0
     */

    private String filePathResult;
    private int stnId;
    private int id;

    public String getFilePathResult() {
        return filePathResult;
    }

    public void setFilePathResult(String filePathResult) {
        this.filePathResult = filePathResult;
    }

    public int getStnId() {
        return stnId;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
