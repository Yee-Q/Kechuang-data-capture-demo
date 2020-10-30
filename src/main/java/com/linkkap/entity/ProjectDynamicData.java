package com.linkkap.entity;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class ProjectDynamicData {

    private Integer pid;
    private String stockAuditNum;
    private String stockAuditName;
    private String url;

    public ProjectDynamicData() {

    }

    public ProjectDynamicData(Integer pid, String stockAuditNum, String stockAuditName, String url) {
        this.pid = pid;
        this.stockAuditNum = stockAuditNum;
        this.stockAuditName = stockAuditName;
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getStockAuditNum() {
        return stockAuditNum;
    }

    public void setStockAuditNum(String stockAuditNum) {
        this.stockAuditNum = stockAuditNum;
    }

    public String getStockAuditName() {
        return stockAuditName;
    }

    public void setStockAuditName(String stockAuditName) {
        this.stockAuditName = stockAuditName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LinkTypeData{" +
                "id=" + pid +
                ", stockAuditNum='" + stockAuditNum + '\'' +
                ", publisher='" + stockAuditName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
