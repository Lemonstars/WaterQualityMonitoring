package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo;

import java.util.List;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/13
 * @time : 下午2:40
 */

public class HistoryRecordVO {

    private List<PdfVO> pdfVOList;
    private List<String> picList;

    public List<PdfVO> getPdfVOList() {
        return pdfVOList;
    }

    public void setPdfVOList(List<PdfVO> pdfVOList) {
        this.pdfVOList = pdfVOList;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
