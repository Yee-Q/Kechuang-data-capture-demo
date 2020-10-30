package com.linkkap.entity;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class FileData {

    private Integer fid;
    private String stockAuditName;
    private String prospectusUrl;
    private String reportUrl;

    public FileData() {

    }

    public FileData(Integer fid, String stockAuditName, String prospectusUrl, String reportUrl) {
        this.fid = fid;
        this.stockAuditName = stockAuditName;
        this.prospectusUrl = prospectusUrl;
        this.reportUrl = reportUrl;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getStockAuditName() {
        return stockAuditName;
    }

    public void setStockAuditName(String stockAuditName) {
        this.stockAuditName = stockAuditName;
    }

    public String getProspectusUrl() {
        return prospectusUrl;
    }

    public void setProspectusUrl(String prospectusUrl) {
        this.prospectusUrl = prospectusUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id='" + fid + '\'' +
                ", publisher='" + stockAuditName + '\'' +
                ", prospectusUrl='" + prospectusUrl + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                '}';
    }
}
